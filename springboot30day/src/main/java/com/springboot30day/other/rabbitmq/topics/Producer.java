package com.springboot30day.other.rabbitmq.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springboot30day.other.rabbitmq.ConnectionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 生產者
// exchange type : Topic
public class Producer {
    static final String TOPIC_EXCHANGE = "topic_exchange";
    static final String TOPIC_QUEUE1 = "topic_queue1";
    static final String TOPIC_QUEUE2 = "topic_queue2";

    public static void main(String[] args) {

        try (
                // 建立連接
                Connection conn = ConnectionUtil.getConnection();
                // 建立頻道
                Channel channel = conn.createChannel();) {

            // channel declare exchange :宣告exchange
            /*
             * @param exchange , exchange 名稱
             * 
             * @param type , 類型
             *
             */
            channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);


            // 發送訊息
            String msg = "routing key: item.insert";
            channel.basicPublish(TOPIC_EXCHANGE, "item.insert", null, msg.getBytes());
            log.info(msg);

            msg = "routing key: item.update";
            channel.basicPublish(TOPIC_EXCHANGE, "item.update", null, msg.getBytes());
            log.info(msg);

            msg = "routing key: item.delete";
            channel.basicPublish(TOPIC_EXCHANGE, "item.delete", null, msg.getBytes());
            log.info(msg);

            msg = "routing key: product.delete";
            channel.basicPublish(TOPIC_EXCHANGE, "product.delete", null, msg.getBytes());
            log.info(msg);
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
