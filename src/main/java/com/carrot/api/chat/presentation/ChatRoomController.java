package com.carrot.api.chat.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatRoomController {

    @GetMapping("/chat-index")
    public String chatIndex() {
        return "chat-index";
    }

    @GetMapping("/chat-room")
    public String chatRoomIndex() {
        return "chat-rooms";
    }
}
