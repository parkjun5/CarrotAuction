package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.global.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "auctionRoom", description = "경매장 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auctionRoom")
public class AuctionRoomController {

    private final AuctionRoomService auctionService;

    @GetMapping("/{auctionRoomId}")
    public ResponseEntity<ApiResponse<Object>> getAuctionRoom
            (@PathVariable("auctionRoomId") Long auctionRoomId) {
        return ResponseEntity.ok()
                .body(ApiResponse.success("AuctionRoom", auctionService.findAuctionInfoById(auctionRoomId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createAuctionRoom
            (@RequestBody @Valid AuctionRequest auctionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("AuctionRoom", auctionService.createAuctionRoom(auctionRequest)));
    }

    @PostMapping("/{auctionRoomId}")
    public ResponseEntity<ApiResponse<Object>> updateAuctionRoom
            (@PathVariable("auctionRoomId") Long auctionRoomId, @RequestBody @Valid AuctionRequest auctionRequest) {

        return ResponseEntity.ok()
                .body(ApiResponse.success("AuctionRoom", auctionService.updateAuctionRoom(auctionRoomId, auctionRequest)));
    }

    @DeleteMapping("/{auctionRoomId}")
    public ResponseEntity<ApiResponse<Object>> deleteAuctionRoom
            (@PathVariable("auctionRoomId") Long auctionRoomId) {
        return ResponseEntity.ok().body(ApiResponse.success("deletedRoomId", auctionService.deleteAuctionRoom(auctionRoomId)));
    }

}
