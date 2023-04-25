package com.carrot.chat.presentation;


import com.carrot.chat.application.ChatService;
import com.carrot.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final Sinks.Many<Object> chatSink;

    @PostMapping("/chat")
    public void addChat() {
        Flux<String> delaidElements = Flux.just("1", "2", "3", "4", "5").delayElements(Duration.ofSeconds(1));
        delaidElements.subscribe(s -> chatSink.emitNext(s, Sinks.EmitFailureHandler.FAIL_FAST));
    }

    @GetMapping("/chatMessages/{roomId}")
    public Flux<ChatMessage> addChat(@PathVariable Long roomId) {
        return chatService.findAllByRoomId(roomId);
    }
}
