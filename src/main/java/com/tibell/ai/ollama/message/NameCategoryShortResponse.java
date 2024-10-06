package com.tibell.ai.ollama.message;

import com.tibell.ai.ollama.dto.NameCategoryShort;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@SuperBuilder
public class NameCategoryShortResponse extends MessageResponse {
    private UUID id;
    private NameCategoryShort response;
    private String model;
    private Long[] context;

    public NameCategoryShortResponse(UUID id, MessageType messageType,
                                     NameCategoryShort response,
                                     String model, Long[] context) {
        super(messageType);
        this.response = response;
    }
}
