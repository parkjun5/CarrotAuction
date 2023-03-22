package com.carrot.auction.domain.bid.dto.bid;

import com.carrot.auction.domain.bid.domain.entity.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidMapper {
    @Mapping(source = "bid.biddingPrice", target = "biddingPrice")
    @Mapping(source = "bid.biddingTime", target = "biddingTime")
    BidResponse toResponseByEntities(String roomName, Bid bid, String bidderName);

    Bid toEntityByRequest(BidRequest bidRequest);

}
