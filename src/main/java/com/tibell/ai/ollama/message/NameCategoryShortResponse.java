package com.tibell.ai.ollama.message;

import com.tibell.ai.ollama.dto.NameCategoryShort;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class NameCategoryShortResponse extends MessageResponse {
    private NameCategoryShort response;
    private String model;
    private Long[] context;

    public NameCategoryShortResponse(MessageType messageType,
                                     NameCategoryShort response,
                                     String model, Long[] context) {
        super(messageType);
        this.response = response;
    }
}
