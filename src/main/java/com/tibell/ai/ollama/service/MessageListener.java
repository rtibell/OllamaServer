package com.tibell.ai.ollama.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibell.ai.ollama.message.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MessageListener {
    private static ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    OllamaStructureChatREST ollamaStructuredChat;
    //OllamaStructuredChatAPI ollamaStructuredChat;

    @Value("${rabbitmq.queue.responce_queue}")
    private String responseQueue;

    @RabbitListener(queues = "${rabbitmq.queue.command_queue}")
    public void receiveMessage(String message, @Headers Map<String, Object> headers) {
        log.info("Received message: {}", message);
        String correlationId = (String) headers.get("correlation_id");
        String command = (String) headers.get("command_type");
        MessageResponse response = processMessage(message, command);
        sendMessage(response);
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    private void sendMessage(String messageText) {
        amqpTemplate.convertAndSend(responseQueue, messageText);
    }

    private void sendMessage(MessageResponse message)  {
        try {
            String messageText = MAPPER.writeValueAsString(message);
            amqpTemplate.convertAndSend(responseQueue, messageText);
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
        MessageCommand messageCommand = null;
        MessageResponse messageResponse = null;
        try {
            switch (command) {
                case "NAME_CATEGORY_ONELINER":
                    messageCommand = MAPPER.readValue(message, StructuredChatMessageCommand.class);
                    ollamaStructuredChat.queryNameCategoryOneliner(messageCommand);
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
