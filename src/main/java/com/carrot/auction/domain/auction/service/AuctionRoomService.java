package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.global.dto.ApiResponse;

public interface AuctionRoomService {
    AuctionRoom findAuctionInfoById(Long auctionRoomId);

    ApiResponse<Object> createAuctionRoom(CreateAuctionRequest createAuctionRequest);
}
