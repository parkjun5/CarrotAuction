package com.carrot.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class CarrotRabbitMqReceiveApplication {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {

            Connection conn = factory.newConnection();
            Channel channel = conn.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit)");

            DeliverCallback callback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] Received : " + msg);
            };
            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}