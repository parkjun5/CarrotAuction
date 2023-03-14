package com.carrot.auction.domain.auction.controller;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    @Operation(summary = "경매장 리스트 조회", description = "pageable 를 이용하여 경매장 리스트를 조회한다.")
    public ResponseEntity<ApiCommonResponse<Page<AuctionResponse>>> getAuctionRooms(
            @PageableDefault(direction = Sort.Direction.ASC, sort = "id") Pageable pageable
    ) {
        Page<AuctionResponse> responsePage = auctionService.getAuctionRoomsByPageable(pageable);
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_NAME, responsePage));
    }

    @GetMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 하나 조회", description = "id를 이용하여 경매장을 조회한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> getAuctionRoom(
            @PathVariable("auctionRoomId") final Long auctionRoomId
    ) {
        AuctionResponse response = auctionService.findAuctionResponseById(auctionRoomId);
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_NAME, response));
    }

    @PostMapping
    @Operation(summary = "경매장 등록", description = "AuctionRequest를 이용하여 경매장을 등록한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> createAuctionRoom(
            @RequestBody @Valid AuctionRequest auctionRequest
    ) {
        AuctionResponse createdResult = auctionService.createAuctionRoom(auctionRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success(201, "CREATED", AUCTION_RESULT_NAME, createdResult));
    }

    @PostMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 수정", description = "id와 AuctionRequest 이용하여 경매장을 수정한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> updateAuctionRoom(
            @PathVariable("auctionRoomId") final Long auctionRoomId,
            @RequestBody @Valid AuctionRequest auctionRequest
    ) {
        AuctionResponse response = auctionService.updateAuctionRoom(auctionRoomId, auctionRequest);
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_NAME, response));
    }

    @DeleteMapping("/{auctionRoomId}")
    @Operation(summary = "경매장 삭제", description = "id를 이용하여 경매장을 삭제한다.")
    public ResponseEntity<ApiCommonResponse<Object>> deleteAuctionRoom(
            @PathVariable("auctionRoomId") final Long auctionRoomId
    ) {
        Long response = auctionService.deleteAuctionRoom(auctionRoomId);
        return ResponseEntity
                .ok(ApiCommonResponse.success("deletedRoomId", response));
    }

    @PostMapping("/{auctionRoomId}/participant/{userId}")
    @Operation(summary = "참가자 추가", description = "auctionRoomId와 userId를 이용하여 경매장에 참가한다.")
    public ResponseEntity<ApiCommonResponse<AuctionResponse>> participateAuctionRoom(
            @PathVariable("auctionRoomId") final Long auctionRoomId,
            @PathVariable("userId") final Long userId
    ) {
        AuctionResponse response = auctionService.addParticipateAuctionRoom(auctionRoomId, userId);
        return ResponseEntity
                .ok(ApiCommonResponse.success(AUCTION_RESULT_NAME, response));
    }

}
