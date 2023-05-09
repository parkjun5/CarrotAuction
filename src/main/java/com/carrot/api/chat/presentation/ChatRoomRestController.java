package com.carrot.api.chat.presentation;

import com.carrot.api.chat.application.ChatRoomService;
import com.carrot.api.chat.application.dto.ChatRoomRequest;
import com.carrot.api.chat.application.dto.ChatRoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatRoomRestController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/chatroom")
    public ResponseEntity<ChatRoomResponse> chatroom(
            @RequestBody @Valid ChatRoomRequest chatRoomRequest
    ) {
        return ResponseEntity.status(CREATED).body(chatRoomService.createChatRoom(chatRoomRequest));
    }

    @GetMapping("/chatroom")
    public ResponseEntity<Page<ChatRoomResponse>> getChatRooms(
            @PageableDefault(direction = Sort.Direction.ASC, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(chatRoomService.findAll(pageable));
    }

    @PostMapping("/chatroom/{chatRoomId}")
    public ResponseEntity<ChatRoomResponse> updateChatRoom(
            @PathVariable final Long chatRoomId,@RequestBody @Valid ChatRoomRequest chatRoomRequest
    ) {
        return ResponseEntity.ok(chatRoomService.updateChatRoom(chatRoomId, chatRoomRequest));
    }

    @GetMapping("/chatroom/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(
            @PathVariable final Long chatRoomId
    ) {
        return ResponseEntity.ok(chatRoomService.deleteChatRoom(chatRoomId));
    }

}
