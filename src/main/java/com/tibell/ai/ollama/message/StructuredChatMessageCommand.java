package com.tibell.ai.ollama.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StructuredChatMessageCommand extends MessageCommand{
    private String text;
    private String query;

    public StructuredChatMessageCommand(String text, String query, MessageType messageType) {
        super(messageType);
        this.text = text;
        this.query = query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
