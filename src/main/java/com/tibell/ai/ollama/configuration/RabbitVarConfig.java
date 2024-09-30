package com.tibell.ai.ollama.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq.queue")
@Data
public class RabbitVarConfig {
    private String command_queue;
    private String responce_queue;
}
