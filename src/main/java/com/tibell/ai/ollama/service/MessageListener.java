package com.tibell.ai.ollama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibell.ai.ollama.dto.NameCategoryShort;
import com.tibell.ai.ollama.message.*;
import com.tibell.ai.ollama.ollama.OllamaAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class MessageListener {
    private static ObjectMapper MAPPER = new ObjectMapper();

    private final RabbitTemplate responceTemplate;

    @Autowired
    OllamaStructureChatREST ollamaStructuredChat;
    //OllamaStructuredChatAPI ollamaStructuredChat;

    @Value("${rabbitmq.queue.responce_queue}")
    private String responseQueue;

    public MessageListener(RabbitTemplate responceTemplate) {
        this.responceTemplate = responceTemplate;
    }


    @RabbitListener(queues = "${rabbitmq.queue.command_queue}")
    public void receiveMessage(String message, @Headers Map<String, Object> headers) {
        log.info("Received message: {}", message);
        String correlationId = (String) headers.get("correlation_id");
        String command = (String) headers.get("command_type");
        MessageResponse response = processMessage(message, command);
        sendMessage(response, correlationId);
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    private void sendMessage(String messageText) {
        responceTemplate.convertAndSend(responseQueue, messageText);
    }

    private void sendMessage(MessageResponse message, String currelationId)  {
        log.info("Responde to command request, message: {}", message);
        try {
            if (message == null) {
                log.info("No responce message generated");
                return;
            }
            switch (message.getMessageType()) {
                case ERROR:
                    sendError((OllamaError) message);
                    break;
                case CONTACTINFO:
                    //ToDo
                    break;
                case NAME_CATEGORY_ONELINER:
                    NameCategoryShortResponse response = (NameCategoryShortResponse) message;
                    log.info("Sending NAME_CATEGORY_ONELINER response: {}", response.getResponse().toString());
                    String messageText = MAPPER.writeValueAsString(response);
                    responceTemplate.convertAndSend(responseQueue, messageText, m -> {
                        m.getMessageProperties().setHeader("correlation_id", currelationId);
                        return m;
                    });
                    break;
                default:
                    log.info("Unknown message type: {}", message.getMessageType());
            }
        } catch (JsonProcessingException e) {
            log.error("Error sending message", e);
            throw new RuntimeException(e);
        }
    }

    private void sendError(OllamaError message)  {
        String messageText = null;
        try {
            messageText = MAPPER.writeValueAsString(message);
            amqpTemplate.convertAndSend(responseQueue, messageText);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageResponse processMessage(String message, String command) {
        log.info("Processing message: {} command <{}>", message, command);
        MessageResponse messageResponse = null;
        try {
            switch (command) {
                case "NAME_CATEGORY_ONELINER":
                    StructuredChatMessageCommand messageCommand = MAPPER.readValue(message, StructuredChatMessageCommand.class);
                    OllamaAPIResponse apiRespons = ollamaStructuredChat.queryNameCategoryOneliner(messageCommand);
                    NameCategoryShortResponse response = NameCategoryShortResponse.builder()
                                    .messageType(MessageType.NAME_CATEGORY_ONELINER)
                                    .id(messageCommand.getId())
                                    .model(apiRespons.getModel())
                                    .context(apiRespons.getContext())
                                    .response(MAPPER.readValue(apiRespons.getResponse(), NameCategoryShort.class))
                                    .build();
                    //sendMessage(response);
                    messageResponse = response;
                    break;
                default:
                    messageResponse = new OllamaError(MessageType.ERROR, "Unknown command", "Unknown command");
                    log.warn("Unknown command: <{}>", command);
            }
        } catch (JsonProcessingException e) {
            log.warn("Error processing message, code: {}", e);
            messageResponse = new OllamaError(MessageType.ERROR, e.getMessage(), "Error processing message");
        }
        return messageResponse;
    }
}
