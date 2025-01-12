package com.tibell.ai.ollama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tibell.ai.ollama.dto.NameCategoryShort;
import com.tibell.ai.ollama.mapping.CustomNameCategoryShortDeserializer;
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
    private static ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(NameCategoryShort.class, new CustomNameCategoryShortDeserializer());
        MAPPER.registerModule(module);
    }

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
        log.debug("Received message: {}, headers: {}", message, headers);
        String correlationId = null;
        if (headers.get("amqp_correlationId") != null) { correlationId = headers.get("amqp_correlationId").toString(); }
        if (correlationId == null && headers.get("correlation_id") != null) { headers.get("correlation_id").toString(); }
        String command = (String) headers.get("command_type");
        MessageResponse response = processMessage(message, command);
        sendMessage(response, correlationId);
    }

    private void sendMessage(String messageText) {
        responceTemplate.convertAndSend(responseQueue, messageText);
    }

    private void sendMessage(MessageResponse message, String currelationId)  {
        log.debug("Responde to command request, message: {}, curr_id: {}", message, currelationId);
        try {
            if (message == null) {
                log.info("No responce message generated, null message sent");
                return;
            }
            switch (message.getMessageType()) {
                case ERROR:
                    sendError((OllamaError) message, currelationId);
                    break;
                case CONTACTINFO:
                    //ToDo
                    break;
                case NAME_CATEGORY_ONELINER:
                    NameCategoryShortResponse response = (NameCategoryShortResponse) message;
                    log.debug("Sending NAME_CATEGORY_ONELINER response: {}", response.getResponse().toString());
                    String messageText = MAPPER.writeValueAsString(response);
                    responceTemplate.setRoutingKey("responce-ollama-key");
                    responceTemplate.convertAndSend(responseQueue, messageText, m -> {
                        m.getMessageProperties().setHeader("correlation_id", currelationId);
                        m.getMessageProperties().setHeader("command_type", "NAME_CATEGORY_ONELINER");
                        m.getMessageProperties().setCorrelationId(currelationId);
                        log.debug("Message sent: {}, properties: {}", m, m.getMessageProperties());
                        return m;
                    });
                    break;
                default:
                    log.info("Unknown message type: {}", message.getMessageType());
            }
        } catch (JsonProcessingException e) {
            log.error("Error sending message Error: ", e);
            throw new RuntimeException(e);
        }
    }

    private void sendError(OllamaError message, String currelationId)  {
        String messageText = null;
        try {
            messageText = MAPPER.writeValueAsString(message);
            responceTemplate.convertAndSend(responseQueue, messageText, m -> {
                m.getMessageProperties().setHeader("correlation_id", currelationId);
                m.getMessageProperties().setHeader("command_type", "ERROR");
                m.getMessageProperties().setCorrelationId(currelationId);
                return m;
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageResponse processMessage(String message, String command) {
        log.debug("Processing message: {} command <{}>", message, command);
        MessageResponse messageResponse = null;
        try {
            switch (command) {
                case "NAME_CATEGORY_ONELINER":
                    StructuredChatMessageCommand messageCommand = MAPPER.readValue(message, StructuredChatMessageCommand.class);
                    try {
                        OllamaAPIResponse apiRespons = ollamaStructuredChat.queryNameCategoryOneliner(messageCommand);
                        log.debug("API response: {}", apiRespons);
                        NameCategoryShortResponse response = NameCategoryShortResponse.builder()
                                .messageType(MessageType.NAME_CATEGORY_ONELINER)
                                .id(messageCommand.getId())
                                .model(apiRespons.getModel())
                                .context(apiRespons.getContext())
                                .response(MAPPER.readValue(apiRespons.getResponse(), NameCategoryShort.class))
                                .total_duration(apiRespons.getTotal_duration())
                                .load_duration(apiRespons.getLoad_duration())
                                .prompt_eval_count(apiRespons.getPrompt_eval_count())
                                .prompt_eval_duration(apiRespons.getPrompt_eval_duration())
                                .eval_count(apiRespons.getEval_count())
                                .eval_duration(apiRespons.getEval_duration())
                                .running_time(apiRespons.getRunning_time())
                                .build();
                        log.debug("Response: {}", response);
                        //sendMessage(response);
                        messageResponse = response;
                    } catch (Exception e) {
                        log.error("Error in queryNameCategoryOneliner: {}", e.getMessage());
                        messageResponse = new OllamaError(MessageType.ERROR, e.getMessage(), "Error chatting with Ollama");
                    }

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
