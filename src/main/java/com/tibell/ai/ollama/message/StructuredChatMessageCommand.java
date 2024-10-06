package com.tibell.ai.ollama.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class StructuredChatMessageCommand extends MessageCommand{
    private UUID id;
    private String text;
    private String query;

    public StructuredChatMessageCommand(UUID id, String text, String query, MessageType messageType) {
        super(messageType);
        this.text = text;
        this.query = query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
