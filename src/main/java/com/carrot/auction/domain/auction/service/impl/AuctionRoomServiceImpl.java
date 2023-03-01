package com.carrot.auction.domain.auction.service.impl;

import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomServiceImpl implements AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final UserService userService;

    @Override
    public AuctionResponse findAuctionInfoById(Long auctionRoomId) {
        AuctionRoom findAuction = auctionRepository.findById(auctionRoomId)
                .orElseThrow(() -> new NoSuchElementException(auctionRoomId + " 아이디가 존재하지 않습니다."));
        return auctionRoomToResponse(findAuction);
    }

    @Override
    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest auctionRequest) {
        User hostUser = userService.findUserById(auctionRequest.userId())
                .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionRoom auctionRoom = AuctionRoom.createByRequestBuilder()
                .hostUser(hostUser)
                .auctionRequest(auctionRequest)
                .build();
        AuctionRoom save = auctionRepository.save(auctionRoom);
        return ApiResponse.success("data", save);
    }

}
