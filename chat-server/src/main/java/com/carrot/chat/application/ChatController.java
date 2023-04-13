package com.carrot.chat.application;

import com.carrot.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    @GetMapping("/index")
    public Mono<String> getIndex() {
        return Mono.just("index");
    }

    @GetMapping("/")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "WebFlux");
    }

    @GetMapping("/stream")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        return Flux.fromStream(stream.limit(20000))
                .map(i -> Collections.singletonMap("value", i));
    }

    @PostMapping("/echo")
    Flux<ChatMessage> echo(@RequestBody Flux<ChatMessage> body) {
        return body.map(each -> {
            log.info("[" + Thread.currentThread().getName() + "]each = " + each);
            return each;
        });
    }
}
