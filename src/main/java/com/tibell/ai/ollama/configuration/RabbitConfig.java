package com.tibell.ai.ollama.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RabbitConfig {
    private static final String COMMAND_EXCHANGE = "";
    private static final String RESPONCE_EXCHANGE = "";
    private static final String COMMAND_ROUTING_KEY = "";
    private static final String RESPONCE_ROUTING_KEY = "";

    @Value("${rabbitmq.queue.command_queue}")
    private String commandQueueName;

    @Value("${rabbitmq.queue.responce_queue}")
    private String responceQueueName;

    @Bean
    public Queue commandQueue() {
        return new Queue(commandQueueName);
    }

    @Bean
    public Queue responceQueue() {
        return new Queue(responceQueueName);
    }

    @Bean
    public Exchange commandExchange() {
        return ExchangeBuilder.directExchange(COMMAND_EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange responceExchange() {
        return ExchangeBuilder.directExchange(RESPONCE_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding commandBinding() {
        return BindingBuilder
                .bind(commandQueue())
                .to(commandExchange())
                .with(COMMAND_ROUTING_KEY)
                .noargs();
    }

    @Bean
    public Binding responceBinding() {
        return BindingBuilder
                .bind(responceQueue())
                .to(responceExchange())
                .with(RESPONCE_ROUTING_KEY)
                .noargs();
    }

}
