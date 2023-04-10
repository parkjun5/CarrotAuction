package com.carrot.web_mvc.bid.application.dto;

import com.carrot.web_mvc.bid.domain.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidMapper {
    @Mapping(source = "bid.biddingPrice", target = "biddingPrice")
    @Mapping(source = "bid.biddingTime", target = "biddingTime")
    BidResponse toResponseByEntities(String auctionName, Bid bid, String bidderName);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auction", ignore = true)
    Bid toEntityByRequest(BidRequest bidRequest);

}