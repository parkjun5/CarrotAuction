package com.carrot.core.auctionroom.application.dto;

import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.user.domain.User;
import com.carrot.core.user.application.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuctionRoomMapper {
    @Mapping(source = "request.password", target = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auctionParticipation", ignore = true)
    @Mapping(target = "auctions", ignore = true)
    AuctionRoom toEntityByRequestAndUser(User hostUser, AuctionRoomRequest request);

    @Mapping(source = "auctionRoom.id", target = "auctionRoomId")
    AuctionRoomResponse toResponseByEntityAndNames(AuctionRoom auctionRoom, UserResponse userResponse, Set<String> nameOfParticipants);

}
