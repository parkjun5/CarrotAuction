package com.carrot.core.auction.application.dto;

import com.carrot.core.auction.domain.Auction;
import com.carrot.core.bidrule.application.dto.BidRuleResponse;
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
