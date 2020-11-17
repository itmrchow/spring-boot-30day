package com.springboot30day.other.rabbitmq.routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springboot30day.other.rabbitmq.ConnectionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 生產者
// exchange type : fanout
public class Producer {
    static final String DIRECT_EXCHANGE = "direct_exchange";
    static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";

    

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
            channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);

            // channel declare queue
            /*
             * @param queue 序列名
             * 
             * @param durable 是否要持久化(伺服器重啟是否在)
             * 
             * @param exclusive 是否獨佔連接(通常false希望一個conn有多個channel)
             * 
             * @param autoDelete 不使用的時候自動刪除
             * 
             * @param arguments 其他參數
             */
            channel.queueDeclare(DIRECT_QUEUE_INSERT, true, false, false, null);
            channel.queueDeclare(DIRECT_QUEUE_UPDATE, true, false, false, null);

            //queue綁定exchange
            channel.queueBind(DIRECT_QUEUE_INSERT, DIRECT_EXCHANGE, "insert");
            channel.queueBind(DIRECT_QUEUE_UPDATE, DIRECT_EXCHANGE, "update");

            // 發送訊息
            String msg = "routing key: insert";
            channel.basicPublish(DIRECT_EXCHANGE, "insert", null, msg.getBytes());
            log.info(msg);

            msg = "routing key: update";
            channel.basicPublish(DIRECT_EXCHANGE, "update", null, msg.getBytes());
            log.info(msg);
            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
