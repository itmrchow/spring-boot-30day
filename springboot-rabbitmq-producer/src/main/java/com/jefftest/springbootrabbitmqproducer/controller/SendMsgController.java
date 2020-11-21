package com.jefftest.springbootrabbitmqproducer.controller;

import com.jefftest.springbootrabbitmqproducer.config.RabbitMQConfig;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 發送訊息的測試
 */
@RestController
public class SendMsgController {
    // 注入RabbitMQ模板
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendmsg")
    public String sendMsg(@RequestParam String msg, @RequestParam String key) {

        /**
         * 發送訊息
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, key, msg);

        return "發送Msg";
    }

}
