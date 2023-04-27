package com.carrot.api.chat.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatFomController {

    @GetMapping("/chat-index")
    public String chatIndex() {
        return "chat-index";
    }
}
