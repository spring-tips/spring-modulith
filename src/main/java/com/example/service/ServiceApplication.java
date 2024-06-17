package com.example.service;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.moments.HourHasPassed;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @EventListener
    void on(HourHasPassed hourHasPassed) {
        System.out.println(hourHasPassed.getTime());
    }

    private static final String ORDERS_DESTINATION = "orders";

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ORDERS_DESTINATION).noargs();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable().build();
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.directExchange(ORDERS_DESTINATION).durable(true).build();
    }
}
