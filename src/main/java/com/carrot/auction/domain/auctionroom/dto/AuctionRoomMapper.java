package com.carrot.auction.domain.auctionroom.dto;

import com.carrot.auction.domain.auctionroom.domain.entity.AuctionRoom;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuctionRoomMapper {
    @Mapping(source = "request.password", target = "password")
    @Mapping(source = "hostUser", target = "hostUser")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auctionParticipation", ignore = true)
    AuctionRoom toEntityByRequestAndUser(User hostUser, AuctionRoomRequest request);

    @Mapping(source = "auctionRoom.id", target = "auctionRoomId")
    @Mapping(source = "nameOfParticipants", target = "nameOfParticipants")
    AuctionRoomResponse toResponseByEntityAndNames(AuctionRoom auctionRoom, UserResponse userResponse, Set<String> nameOfParticipants);

}
