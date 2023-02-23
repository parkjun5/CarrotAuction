package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Tag(name = "auctionRoom", description = "경매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auctionRoom")
public class AuctionRoomController {

    private final AuctionRoomService auctionService;

    @GetMapping("/{auctionRoomId}")
    public ResponseEntity<ApiResponse<Object>> getAuctionRoom
            (@PathVariable("auctionRoomId") Long auctionRoomId) {

        AuctionRoom auctionRoom = auctionService.findAuctionInfoById(auctionRoomId)
                .orElseThrow(() -> new NoSuchElementException(auctionRoomId + " 아이디가 존재하지 않습니다."));

        return ResponseEntity.ok(ApiResponse.success("AuctionRoom", auctionRoom));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createAuctionRoom
            (@RequestBody @Valid CreateAuctionRequest createAuctionRequest) {
        return ResponseEntity.ok(auctionService.createAuctionRoom(createAuctionRequest));
    }

}
