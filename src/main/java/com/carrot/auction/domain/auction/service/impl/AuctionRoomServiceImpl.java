package com.carrot.auction.domain.auction.service.impl;

import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import com.carrot.auction.domain.auction.dto.CreateAuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomServiceImpl implements AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final UserService userService;

    @Override
    public Optional<AuctionRoom> findAuctionInfoById(Long auctionRoomId) {
        return auctionRepository.findById(auctionRoomId);
    }

    @Override
    @Transactional
    public ApiResponse<Object> createAuctionRoom(CreateAuctionRequest createAuctionRequest) {
        User hostUser = userService.findUserById(createAuctionRequest.userId())
                .orElseThrow(() -> new NoSuchElementException(createAuctionRequest.userId() + "계정이 존재하지 않습니다."));
        AuctionRoom auctionRoom = AuctionRoom.createByRequestBuilder()
                .hostUser(hostUser)
                .createAuctionRequest(createAuctionRequest)
                .build();
        AuctionRoom save = auctionRepository.save(auctionRoom);
        return ApiResponse.success("data", save);
    }

}
