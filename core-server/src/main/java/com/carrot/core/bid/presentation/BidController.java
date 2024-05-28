package com.carrot.core.bid.presentation;

import com.carrot.core.bid.application.dto.BidRequest;
import com.carrot.core.bid.application.dto.BidResponse;
import com.carrot.core.bid.application.BidService;
import com.carrot.core.common.dto.ApiCommonResponse;
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
    @Operation(summary = "입찰 가격 조회", description = "입찰 가격을 조회한다.")
    public ResponseEntity<ApiCommonResponse<BidResponse>> getBid(
            @PathVariable final Long bidId
    ) {
        BidResponse response = bidService.findBidById(bidId);
        return ResponseEntity.ok(ApiCommonResponse.success("bid", response));
    }

    @PostMapping
    @Operation(summary = "가격 입찰", description = "가격을 입찰한다.")
    public ResponseEntity<ApiCommonResponse<BidResponse>> updateBid(
            @RequestBody @Valid BidRequest bidRequest
    ) {
        BidResponse response = bidService.bidding(bidRequest);
        return ResponseEntity.ok(ApiCommonResponse.success("bid", response));
    }
}
