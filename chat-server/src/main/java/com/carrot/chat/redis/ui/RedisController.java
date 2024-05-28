package com.carrot.chat.redis.ui;

import com.carrot.chat.redis.application.RedisContainerManager;
import com.carrot.chat.redis.application.RedisPubService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController("/redis")
@Profile("redis-pub-sub")
public class RedisController {

    private final RedisPubService redisPubService;
    private final RedisContainerManager redisContainerManager;

    public RedisController(RedisPubService redisPubService, RedisContainerManager redisContainerManager) {
        this.redisPubService = redisPubService;
        this.redisContainerManager = redisContainerManager;
    }

    @PostMapping("/chat")
    public String pubSubTest(@RequestBody MessageObject messageObject) {
        redisPubService.sendMessage(messageObject);
        return "chat success";
    }

    @PostMapping("/sub/{chatRoomId}")
    public String addSub(@PathVariable Long chatRoomId, @RequestParam String name) {
        redisContainerManager.addSub(chatRoomId, name);
        return "added";
    }

    @DeleteMapping("/sub/{chatRoomId}")
    public String deleteSub(@PathVariable Long chatRoomId, @RequestParam String name) {
        redisContainerManager.removeSub(chatRoomId, name);
        return "deleted";
    }

}
