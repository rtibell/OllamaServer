package com.tibell.ai.ollama;

import com.tibell.ai.ollama.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@Slf4j
@SpringBootApplication
@EnableFeignClients
public class OllamaApp {

    public static void main(String[] args) {
        SpringApplication.run(OllamaApp.class, args);
    }
}
