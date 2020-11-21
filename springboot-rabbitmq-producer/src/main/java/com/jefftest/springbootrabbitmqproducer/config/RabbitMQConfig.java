package com.jefftest.springbootrabbitmqproducer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * RabbitMQ 
 * 設定Class 
 */
@Configuration
public class RabbitMQConfig {
    // exchange
    public static final String ITEM_TOPIC_EXCHANGE = "item_topic_exchange";
    // queue
    public static final String ITEM_QUEUE = "item_queue";

    // 宣告exchange
    @Bean("itemTopicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    //宣告queue
    @Bean("itemQueue")
    public Queue iteQueue() {
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    //綁定
    @Bean
    public Binding itemQueueExchange(@Qualifier("itemQueue")Queue queue , @Qualifier("itemTopicExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }
}
