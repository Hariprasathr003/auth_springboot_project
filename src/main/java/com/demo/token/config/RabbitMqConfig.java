package com.demo.token.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue myQueue() // Define a queue
    {
        return new Queue("myQueue", false); // Make it durable
    }

    @Bean
    public TopicExchange myExchange() // Define a topic exchange
    {
        return new TopicExchange("myExchange"); // Corrected the name
    }

    @Bean // Bind the queue to the exchange with a routing key
    public Binding binding() {
        return BindingBuilder.bind(myQueue()).to(myExchange()).with("routing.key");
    }
}
