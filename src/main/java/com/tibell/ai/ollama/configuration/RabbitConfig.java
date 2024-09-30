package com.tibell.ai.ollama.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.queue.command_queue}")
    private String commandQueueName;

    @Bean
    public Queue queue() {
        return new Queue(commandQueueName);
    }

//    @Bean
//    public ConnectionFactory connectionFactory() {
//        return CachingConnectionFactory.createConnectionFactory();
//    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange("");
        template.setRoutingKey("");
        return template;
    }
}
