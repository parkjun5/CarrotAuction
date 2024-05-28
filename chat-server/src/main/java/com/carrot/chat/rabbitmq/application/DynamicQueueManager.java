package com.carrot.chat.rabbitmq.application;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicQueueManager {

    private final RabbitAdmin rabbitAdmin;
    private final Receiver receiver;
    private final ConnectionFactory connectionFactory;
    private final ChatRoomManager chatRoomManager;

    private final ConcurrentHashMap<String, SimpleMessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<Binding>> bindings = new ConcurrentHashMap<>();


    public DynamicQueueManager(RabbitAdmin rabbitAdmin, Receiver receiver, ConnectionFactory connectionFactory, ChatRoomManager chatRoomManager) {
        this.rabbitAdmin = rabbitAdmin;
        this.receiver = receiver;
        this.connectionFactory = connectionFactory;
        this.chatRoomManager = chatRoomManager;
    }

    public void createQueueForUser(String topicName, Long userId) {
        String queueName = "queue-" + userId;
        Queue queue = new Queue(queueName, false);
        rabbitAdmin.declareQueue(queue);

        TopicExchange exchange = new TopicExchange(topicName);
        rabbitAdmin.declareExchange(exchange);

        Binding notificationBinding = BindingBuilder.bind(queue).to(exchange).with("notification." + userId + ".#");
        rabbitAdmin.declareBinding(notificationBinding);

        Binding chatBinding = BindingBuilder.bind(queue).to(exchange).with("chat." + userId + ".#");
        rabbitAdmin.declareBinding(chatBinding);

        bindings.put(queueName, Set.of(notificationBinding, chatBinding));
        createListener(queueName);
    }

    private void createListener(String queueName) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);

        container.setMessageListener(message -> receiver.receiveMessage(new String(message.getBody()),
                message.getMessageProperties().getReceivedRoutingKey()));
        container.setConcurrentConsumers(3);  // 시작 스레드 수
        container.setMaxConcurrentConsumers(10);  // 최대 스레드 수
        container.start();

        listenerContainers.put(queueName, container);
    }

    public Enumeration<String> getQueueNames() {
        return listenerContainers.keys();
    }

    public void removeQueueForUser(String userId) {
        String queueName = "queue-" + userId;
        SimpleMessageListenerContainer container = listenerContainers.remove(queueName);
        if (container != null) {
            container.stop();
        }

        Set<Binding> userBindings = bindings.remove(queueName);
        if (userBindings != null) {
            for (Binding binding : userBindings) {
                rabbitAdmin.removeBinding(binding);
            }
        }

        rabbitAdmin.deleteQueue(queueName);
    }

    public void sendMessageToChatRoom(String exchangeName, Long chatRoomId,
                                      Long senderId, String message
    ) {
        Set<Long> users = chatRoomManager.getUsersInChatRoom(chatRoomId, senderId);
        for (Long userId : users) {
            if (!userId.equals(senderId)) {
                String routingKey = "notification" + "." + chatRoomId + "." + userId;
                rabbitAdmin.getRabbitTemplate().convertAndSend(exchangeName, routingKey, message);
            }
        }
    }
}
