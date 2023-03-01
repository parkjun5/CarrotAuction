package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;

public interface AuctionRoomService {
    AuctionResponse findAuctionInfoById(Long auctionRoomId);

    AuctionResponse createAuctionRoom(AuctionRequest auctionRequest);

    AuctionResponse updateAuctionRoom(Long auctionRoomId, AuctionRequest auctionRequest);

    Long deleteAuctionRoom(Long auctionRoomId);
}
