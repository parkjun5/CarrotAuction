package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(target = "id", ignore = true)
    Auction toEntityByRequest(AuctionRequest request);

    AuctionResponse toResponseByEntity(Auction auctionRoom);

}
