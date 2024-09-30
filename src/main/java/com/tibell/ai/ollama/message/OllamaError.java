package com.tibell.ai.ollama.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
public class OllamaError extends  MessageResponse{
    private String message;
    private String error;

    public OllamaError(MessageType messageType, String message, String error) {
        super(messageType);
        this.message = message;
        this.error = error;
    }
}
