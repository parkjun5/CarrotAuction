package com.carrot.chat.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class ChatHandlerTest {

    @Test
    @DisplayName("Flux 사용 예제")
    void fluxCreate() {
        //given
        Flux<String> flux1 = Flux.just("1", "하이", "플럭스");
        Disposable subscribeNoPublish = flux1
                .subscribe(data -> System.out.println("[ " + Thread.currentThread().getName() + "] data = " + data));
        assertThat(subscribeNoPublish.isDisposed()).isTrue();

        Flux<String> flux2 = Flux.fromIterable(Arrays.asList("1", "하이", "플럭스","6", "하이2", "플럭스2"));
        String s = Flux.fromIterable(Arrays.asList("1", "하이", "플럭스", "6", "하이2", "플럭스2"))
                .filter(each -> each.startsWith("플럭스"))
                .blockFirst();
        System.out.println("s = " + s);

        flux2.subscribeOn(Schedulers.parallel())
                .subscribe(data -> System.out.println("[ " + Thread.currentThread().getName() + "] flux 2 data = " + data));

        Flux<Integer> flux3 = Flux.range(1, 10);
        //when
        Mono<Object> mono1 = Mono.empty();
        Mono<Object> mono2 = Mono.just("one data");
        //then
    }

    @Test
    void pubOnSubOn() throws InterruptedException {
        // 각자 모아 모아 실행
        Flux<String> parallelFlux = Flux.fromIterable(Arrays.asList("1", "하이", "플럭스","6", "하이2", "플럭스2"))
                .filter(str -> {
                    System.out.println("[ " + Thread.currentThread().getName() + "] str = " + str);
                    return str.startsWith("하이");
                })
                .publishOn(Schedulers.parallel())
                .map(eachStr -> {
                    String concat = eachStr.concat(" 매핑!");
                    System.out.println("[ " + Thread.currentThread().getName() + "] concat = " + concat);
                    return concat;
                })
                .publishOn(Schedulers.parallel())
                .map(a -> {
                    System.out.println("[ " + Thread.currentThread().getName() + "] a = " + a);
                    return a;
                })
                .publishOn(Schedulers.parallel());
        Disposable publishOnSubscribe = parallelFlux
                .subscribeOn(Schedulers.parallel())
                .subscribe(str -> System.out.println("[ " + Thread.currentThread().getName() + "] parallelFlux = " + str));
        assertThat(publishOnSubscribe.isDisposed()).isFalse();

        System.out.println();
        // 하나씩 사용
        Flux<String> noParallelFlux = Flux.fromIterable(Arrays.asList("1", "하이", "플럭스","6", "하이2", "플럭스2"))
                .filter(str -> {
                    System.out.println("[ " + Thread.currentThread().getName() + "] str = " + str);
                    return str.startsWith("하이");
                })
                .map(eachStr -> {
                    String concat = eachStr.concat(" 매핑!");
                    System.out.println("[ " + Thread.currentThread().getName() + "] concat = " + concat);
                    return concat;
                })
                .map(a -> {
                    System.out.println("[ " + Thread.currentThread().getName() + "] a = " + a);
                    return a;
                });
        Disposable subscribe = noParallelFlux
                .subscribe(str -> System.out.println("[ " + Thread.currentThread().getName() + "] parallelFlux = " + str));
        assertThat(subscribe.isDisposed()).isTrue();

    }
}