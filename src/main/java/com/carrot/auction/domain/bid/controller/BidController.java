package com.carrot.auction.domain.bid.controller;

import com.carrot.auction.domain.bid.dto.BidRequest;
import com.carrot.auction.domain.bid.dto.BidResponse;
import com.carrot.auction.domain.bid.service.BidService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bid")
public class BidController {

    private final BidService bidService;

    @GetMapping("/{bidId}")
    @Operation(summary = "가격 입찰", description = "가격을 입찰한다.")
    public ResponseEntity<ApiCommonResponse<BidResponse>> getBid(
            @PathVariable final Long bidId
    ) {
        BidResponse response = bidService.findBidById(bidId);
        return ResponseEntity.ok(ApiCommonResponse.success("bid", response));
    }

    @PutMapping
    @Operation(summary = "가격 입찰", description = "가격을 입찰한다.")
    public ResponseEntity<ApiCommonResponse<BidResponse>> updateBid(
            @RequestBody @Valid BidRequest bidRequest
    ) {
        BidResponse response = bidService.bidding(bidRequest);
        return ResponseEntity.ok(ApiCommonResponse.success("bid", response));
    }
}
