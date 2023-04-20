package com.carrot.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class ChatRoom {
    private String id;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void initId() {
        this.id = UUID.randomUUID().toString();
    }
}
