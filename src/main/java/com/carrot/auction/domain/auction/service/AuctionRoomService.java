package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;

public interface AuctionRoomService {
    AuctionResponse findAuctionInfoById(Long auctionRoomId);

    AuctionResponse createAuctionRoom(AuctionRequest auctionRequest);

    ApiResponse<Object> createAuctionRoom(CreateAuctionRequest createAuctionRequest);
}
