package com.carrot.chat.queue.ui;

import com.carrot.chat.queue.application.RedisContainerManager;
import com.carrot.chat.queue.application.RedisPubService;
import org.springframework.web.bind.annotation.*;

@RestController
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
