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
    private Long total_duration;
    private Long load_duration;
    private Integer prompt_eval_count;
    private Long prompt_eval_duration;
    private Integer eval_count;
    private Long eval_duration;
    private Long running_time;



    public NameCategoryShortResponse(UUID id, MessageType messageType,
                                     NameCategoryShort response,
                                     String model, Long[] context) {
        super(messageType);
        this.response = response;
    }
}
