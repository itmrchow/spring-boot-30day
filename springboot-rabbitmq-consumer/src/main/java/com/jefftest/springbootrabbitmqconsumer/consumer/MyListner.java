package com.jefftest.springbootrabbitmqconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//監聽
@Component
public class MyListner {

    @RabbitListener(queues = "item_queue")
    public void msg(String msg) {
        System.out.println("Consumer:" + msg);
    }
}
