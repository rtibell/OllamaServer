package com.tibell.ai.ollama.message;

import com.tibell.ai.ollama.dto.NameCategoryShort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NameCategoryShortResponse extends MessageResponse {
    private NameCategoryShort response;

    public NameCategoryShortResponse(MessageType messageType, NameCategoryShort response) {
        super(messageType);
        this.response = response;
    }
}
