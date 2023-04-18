package com.carrot.api.auction.application.dto;

import com.carrot.api.auction.domain.Auction;
import com.carrot.api.bidrule.application.dto.BidRuleResponse;
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
