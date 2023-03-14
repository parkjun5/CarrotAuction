package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuctionMapper {
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "hostUser", target = "hostUser")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auctionParticipation", ignore = true)
    @Mapping(target = "auctionStatus", ignore = true)
    AuctionRoom toEntityByRequestAndUser(User hostUser, AuctionRequest request);

    @Mapping(source = "auctionRoom.id", target = "auctionRoomId")
    @Mapping(source = "nameOfParticipants", target = "nameOfParticipants")
    AuctionResponse toResponseByEntityAndNames(AuctionRoom auctionRoom, UserResponse userResponse, Set<String> nameOfParticipants);

}
