package com.tibell.ai.ollama.ollama;

// https://github.com/ollama/ollama/blob/main/docs/api.md

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class OllamaAPIGenerateRequest {
    private String model;
    private String prompt;
    private String suffix;
    private String format;
    private OllamaAPIOptions options;
    private Boolean stream;
    private Boolean raw;
    private Integer keep_alive;

}
