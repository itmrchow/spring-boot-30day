package com.springboot30day.other.rabbitmq.work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.springboot30day.other.rabbitmq.ConnectionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 生產者
public class Producer {
    public static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) {

        try (
                // 建立連接
                Connection conn = ConnectionUtil.getConnection();
                // 建立頻道
                Channel channel = conn.createChannel();) {

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
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // 發送30條訊息
            for (int i = 1; i <= 30; i++) {
                // 發送訊息
                String msg = "安安你好--訊息:" + i;
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                log.info("已發送訊息:" + msg);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
