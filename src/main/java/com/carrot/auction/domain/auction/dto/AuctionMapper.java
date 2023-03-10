package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "hostUser", target = "hostUser")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auctionParticipation", ignore = true)
    @Mapping(target = "auctionStatus", ignore = true)
    AuctionRoom toAuctionEntityByRequest(User hostUser, AuctionRequest request);

    @Mapping(source = "auctionRoom.id", target = "auctionRoomId")
    @Mapping(source = "nameOfParticipants", target = "nameOfParticipants")
    AuctionResponse toAuctionResponseByEntity(AuctionRoom auctionRoom, Set<String> nameOfParticipants);

    @Mapping(source = "auctionRoom.name", target = "roomName")
    @Mapping(source = "auctionRoom.bid.biddingPrice", target = "price")
    @Mapping(source = "auctionRoom.bid.biddingTime", target = "biddingTime")
    @Mapping(source = "bidder.nickname", target = "bidderName")
    BiddingResponse toBiddingResponseByEntity(AuctionRoom auctionRoom, User bidder);
}
