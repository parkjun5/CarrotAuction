package com.carrot.core.auction.presentation;

import com.carrot.core.auction.application.dto.AuctionRequest;
import com.carrot.core.auction.application.dto.AuctionResponse;
import com.carrot.core.auction.application.AuctionService;
import com.carrot.core.common.dto.ApiCommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuctionController {
    private final AuctionService auctionService;
    private static final String AUCTION_RESULT_MESSAGE = "AuctionRoom";

    @GetMapping("/auctionRoom/{auctionRoomId}/auctions")
    public ResponseEntity<ApiCommonResponse<List<AuctionResponse>>> getAuctions(
            @PathVariable Long auctionRoomId
    ) {
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_MESSAGE, auctionService.getRoomAuctions(auctionRoomId)));
    }

    @PostMapping("/auctionRoom/{auctionRoomId}/auction")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> createAuctionToAuctionRoom(
            @PathVariable Long auctionRoomId,
            @Valid @RequestBody AuctionRequest auctionRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success(201, "CREATED",
                        AUCTION_RESULT_MESSAGE, auctionService.createAuctionToRoom(auctionRoomId, auctionRequest)));
    }

    @PostMapping("/auction/{auctionId}")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> changeAuction(
            @PathVariable Long auctionId,
            @Valid @RequestBody AuctionRequest auctionRequest
    ) {
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_MESSAGE, auctionService.changeAuctionByRequest(auctionId, auctionRequest)));
    }

    @DeleteMapping("/auction/{auctionId}")
    public ResponseEntity<ApiCommonResponse<Long>> deleteAuction(
            @PathVariable Long auctionId
    ) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("deletedAuctionId", auctionService.deleteAuction(auctionId)));
    }

}
