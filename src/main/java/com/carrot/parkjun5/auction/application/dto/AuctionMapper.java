package com.carrot.parkjun5.auction.application.dto;

import com.carrot.parkjun5.auction.domain.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(target = "id", ignore = true)
    Auction toEntityByRequest(AuctionRequest request);

    AuctionResponse toResponseByEntity(Auction auctionRoom);

}
