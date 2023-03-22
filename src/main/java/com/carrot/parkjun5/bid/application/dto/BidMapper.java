package com.carrot.parkjun5.bid.application.dto;

import com.carrot.parkjun5.bid.domain.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidMapper {
    @Mapping(source = "bid.biddingPrice", target = "biddingPrice")
    @Mapping(source = "bid.biddingTime", target = "biddingTime")
    BidResponse toResponseByEntities(String roomName, Bid bid, String bidderName);

    Bid toEntityByRequest(BidRequest bidRequest);

}
