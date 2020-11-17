package com.springboot30day.other.rabbitmq.ps;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springboot30day.other.rabbitmq.ConnectionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 生產者
// exchange type : fanout
public class Producer {
    static final String FANOUT_EXCHANGE = "fanout_exchange";
    static final String FANOUT_QUEUE1 = "fanout_queue1";
    static final String FANOUT_QUEUE2 = "fanout_queue2";

    

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
            channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

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
            channel.queueDeclare(FANOUT_QUEUE1, true, false, false, null);
            channel.queueDeclare(FANOUT_QUEUE2, true, false, false, null);

            //queue綁定exchange
            channel.queueBind(FANOUT_QUEUE1, FANOUT_EXCHANGE, "");
            channel.queueBind(FANOUT_QUEUE2, FANOUT_EXCHANGE, "");

            // 發送30條訊息
            for (int i = 1; i <= 10; i++) {
                // 發送訊息
                String msg = "安安你好--訊息:" + i;
                channel.basicPublish(FANOUT_EXCHANGE, "", null, msg.getBytes());
                log.info("已發送訊息:" + msg);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
