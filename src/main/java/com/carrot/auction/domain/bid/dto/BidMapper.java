package com.carrot.auction.domain.bid.dto;

import com.carrot.auction.domain.bid.domain.entity.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidMapper {
    @Mapping(source = "bid.biddingPrice", target = "biddingPrice")
    @Mapping(source = "bid.biddingTime", target = "biddingTime")
    BidResponse toResponseByEntities(String roomName, Bid bid, String bidderName);

    @Mapping(source = "roomId", target = "auctionRoom.id")
    Bid toEntityByRequest(BidRequest bidRequest);

}
