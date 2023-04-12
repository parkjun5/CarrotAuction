package com.carrot.parkjun5.auction.application.dto;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auctionRoom", ignore = true)
    @Mapping(target = "bidRules", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "auctionStatus", ignore = true)
    Auction toEntityByRequest(AuctionRequest request);

    @Mapping(source = "auction.id", target = "auctionId")
    AuctionResponse toResponseByEntities(Auction auction, Set<BidRuleResponse> selectedRules);

}
