package com.springboot30day.other.rabbitmq.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.springboot30day.other.rabbitmq.ConnectionUtil;

import lombok.extern.slf4j.Slf4j;

//消費者
@Slf4j
public class Consumer1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立連接
        Connection conn = ConnectionUtil.getConnection();
        // 建立頻道
        Channel channel = conn.createChannel();

        // 宣告exchange
        channel.exchangeDeclare(Producer.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

        // 綁定序列
        channel.queueBind(Producer.FANOUT_QUEUE1, Producer.FANOUT_EXCHANGE, "");

        // new 消費者:監聽訊息
        DefaultConsumer consumer = new DefaultConsumer(channel) {

            // handleDelivery 處理訊息
            // consumerTag:消費者標籤
            // envelope:訊息包內容,可以取得訊息id,routingkey,exchange,訊息與重轉標記
            // properties:訊息屬性
            // body:訊息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {

                log.info("---Consumer1 start---");
                // routingkey
                log.info(envelope.getRoutingKey());
                // exchange
                log.info(envelope.getExchange());
                // 訊息id
                log.info(String.valueOf(envelope.getDeliveryTag()));
                // 訊息
                log.info(new String(body, "UTF-8"));
                
                log.info("---Consumer1 end---");
                log.info("");
                
                try {
                    Thread.sleep(1000);// 1秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        // 監聽訊息
        // 序列名稱
        // 自動確認:true為自動向mq回覆,mq收到回覆會刪除訊息
        channel.basicConsume(Producer.FANOUT_QUEUE1, true, consumer);

    }
}
