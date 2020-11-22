package com.jefftest.springbootrabbitmqproducer;

import com.jefftest.springbootrabbitmqproducer.config.RabbitMQConfig;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqProducerApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	void ttlQueueTest() {
		rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.insert", "ttl 10s");

		MessageProperties msgProp = new MessageProperties();
		msgProp.setExpiration("5000");

		Message msg = new Message("測試設定序列ttl 5秒".getBytes(), msgProp);
		rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.update", msg);
	}

	// 發送15筆資料到exchange
	// 會有5筆送DLX (Queue maxlength = 10)
	// 再送10筆到DLX (TTL = 10s)
	@Test
	void dlxTest() {
		for (int i = 0; i < 15; i++) {
			rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE, "item.insert", "DLX Test msg:" + (i + 1));
		}
	}
}
