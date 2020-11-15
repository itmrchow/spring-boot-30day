package com.springboot30day.other.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static final String QUEUE_NAME = "simple_queue";
    static final String HOST = "127.0.0.1";
    static final int PORT = 5672;

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 位址
        factory.setHost(HOST);
        factory.setPort(PORT);

        // 虛擬主機
        factory.setVirtualHost("/itjeff");
        // 用戶
        factory.setUsername("jeff");
        factory.setPassword("123456");

        Connection conn = factory.newConnection();

        return conn;
    }

    private ConnectionUtil() {
        throw new IllegalStateException("Utility class");
    }

}
