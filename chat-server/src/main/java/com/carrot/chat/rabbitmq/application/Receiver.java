package com.carrot.chat.rabbitmq.application;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static org.springframework.amqp.support.AmqpHeaders.RECEIVED_ROUTING_KEY;

@Slf4j
@Getter
@Component
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message, @Header(RECEIVED_ROUTING_KEY) String routingKey) {
        if (routingKey.startsWith("notification.")) {
            String[] replace = routingKey.replace("notification.", "").split("\\.");
            if (replace.length != 2) {
                throw new IllegalArgumentException("routingKey is Wrong ->" + routingKey);
            }
            handleNotification(message, replace);
        } else if (routingKey.startsWith("chat.")) {
            handleChat(message);
        }
        latch.countDown();
    }

    private void handleNotification(String message, String[] messageValues) {
        String chatRoomId = messageValues[0];
        String userId = messageValues[1];
        log.info("Notification received: " + message + ", chatRoomId: " + chatRoomId + "userId: " + userId);
    }

    private void handleChat(String message) {
        log.info("Chat message received: " + message);
    }

}
