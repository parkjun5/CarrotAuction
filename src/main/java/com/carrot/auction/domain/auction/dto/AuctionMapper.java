package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.user.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "hostUser", target = "hostUser")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "auctionStatus", ignore = true)
    AuctionRoom toEntityByRequest(User hostUser, AuctionRequest request);

    AuctionResponse toResponseByEntity(AuctionRoom auctionRoom);
}
