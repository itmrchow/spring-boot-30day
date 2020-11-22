package com.jefftest.springbootrabbitmqproducer.config;

import org.springframework.amqp.core.Queue;

import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 設定Class
 */
@Configuration
public class RabbitMQConfig {
    // exchange
    public static final String ITEM_TOPIC_EXCHANGE = "item_topic_exchange";
    public static final String DL_EXCHANGE = "dead_exchange";
    // queue
    public static final String ITEM_QUEUE = "item_queue";
    public static final String DL_QUEUE = "my_dlx_queue";
    
    // queue ttl
    public static final String ITEM_QUEUE_TTL = "item_queue_ttl";

    // 宣告exchange
    @Bean("itemTopicExchange")
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(ITEM_TOPIC_EXCHANGE).durable(true).build();
    }

    // 宣告dead exchange
    @Bean("deadExchange")
    public Exchange deadExchange() {
        return ExchangeBuilder.fanoutExchange(DL_EXCHANGE).durable(true).build();
    }


    // 宣告queue
    @Bean("itemQueue")
    public Queue itemQueue() {
        return QueueBuilder.durable(ITEM_QUEUE).build();
    }

    // 宣告queue ttl
    @Bean("itemQueueTTL")
    public Queue itemQueueTTL() {
        return QueueBuilder.durable(ITEM_QUEUE_TTL)
            // 設定Exchange
            .deadLetterExchange(DL_EXCHANGE)
            // ttl
            .ttl(10000)
            // 最大數
            .maxLength(10).build();
    }

    // 宣告 指向exchange中的持久化dead queue
    @Bean("myDLXQueue")
    public Queue myDLXQueue() {
        return QueueBuilder.durable(DL_QUEUE).build();
    }

    // 綁定
    @Bean
    public Binding itemQueueExchange(@Qualifier("itemQueue") Queue queue,
            @Qualifier("itemTopicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }

    @Bean
    public Binding itemQueueTTLExchange(@Qualifier("itemQueueTTL") Queue queue,
            @Qualifier("itemTopicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }

    @Bean
    public Binding myDLXQueueDeadExchange(@Qualifier("myDLXQueue") Queue queue,
            @Qualifier("deadExchange") Exchange exchange) {
                
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }
    

    // @Bean
    // public List<Binding> itemQueueExchange(@Qualifier("itemQueue") Queue queue1,@Qualifier("itemQueueTTL") Queue queue2,
    // @Qualifier("itemTopicExchange") Exchange exchange) {

    //     return Arrays.asList(BindingBuilder.bind(queue1).to(exchange).with("item.#").noargs(),
    //             BindingBuilder.bind(queue2).to(exchange).with("#.update").noargs());
    // }

}
