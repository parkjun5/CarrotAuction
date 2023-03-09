package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.auction.dto.BiddingRequest;
import com.carrot.auction.domain.auction.dto.BiddingResponse;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    private static final String AUCTION_RESULT_NAME ="AuctionRoom";

    @GetMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 조회", description = "id를 이용하여 경매장을 조회한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> getAuctionRoom
            (@PathVariable("auctionRoomId") final Long auctionRoomId) {
        return ResponseEntity.ok()
                .body(ApiCommonResponse.success(AUCTION_RESULT_NAME, auctionService.findAuctionInfoById(auctionRoomId)));
    }

    @PostMapping
    @Operation(summary = "경매장 등록", description = "AuctionRequest를 이용하여 경매장을 등록한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> createAuctionRoom
            (@RequestBody @Valid AuctionRequest auctionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success(201, "CREATED", AUCTION_RESULT_NAME, auctionService.createAuctionRoom(auctionRequest)));
    }

    @PostMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 수정", description = "id와 AuctionRequest 이용하여 경매장을 수정한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> updateAuctionRoom
            (@PathVariable("auctionRoomId") final Long auctionRoomId, @RequestBody @Valid AuctionRequest auctionRequest) {
        return ResponseEntity.ok()
                .body(ApiCommonResponse.success(AUCTION_RESULT_NAME, auctionService.updateAuctionRoom(auctionRoomId, auctionRequest)));
    }

    @DeleteMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 삭제", description = "id를 이용하여 경매장을 삭제한다.")
    public ResponseEntity<ApiCommonResponse<Object>> deleteAuctionRoom
            (@PathVariable("auctionRoomId") final Long auctionRoomId) {
        return ResponseEntity.ok().body(ApiCommonResponse.success("deletedRoomId", auctionService.deleteAuctionRoom(auctionRoomId)));
    }

    @PostMapping("/bid/{auctionRoomId}")
    @Operation(summary = "가격 입찰", description = "가격을 입찰한다.")
    public ResponseEntity<ApiCommonResponse<BiddingResponse>> updateBid
            (@PathVariable("auctionRoomId") final Long auctionRoomId, @RequestBody @Valid BiddingRequest biddingRequest) {
        return ResponseEntity.ok().body(ApiCommonResponse.success("bid", auctionService.updateBid(auctionRoomId, biddingRequest)));
    }

}
