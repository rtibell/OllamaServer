package com.tibell.ai.ollama.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ollama.api")
@Data
public class OllamaVarConfig {
    private String url;
    private String key;
    private String secret;
    private String model;
}
