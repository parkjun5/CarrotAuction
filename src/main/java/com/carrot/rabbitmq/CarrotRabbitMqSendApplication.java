package com.carrot.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class CarrotRabbitMqSendApplication {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "세번쨰";

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("[x] Sent : " + msg);

        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}