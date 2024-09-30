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
    public String prompt;
    public String suffix;
    public String format;
    public OllamaAPIOptions options;
    public Boolean stream;
    public Boolean raw;

}
