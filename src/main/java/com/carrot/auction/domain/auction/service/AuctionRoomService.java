package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.dto.AuctionMapper;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final UserService userService;
    private final AuctionMapper auctionMapper;
    private static final String AUCTION_NOT_FOUND = " 경매장을 찾지 못했습니다.";

    public AuctionResponse findAuctionInfoById(final Long roomId) {
        AuctionRoom findAuction = auctionRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException(roomId + AUCTION_NOT_FOUND));
        return auctionMapper.toResponseByEntity(findAuction);
    }

    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest request) {
        request.validateDateTime();
        User hostUser = userService.findUserById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionRoom auctionRoom = auctionMapper.toEntityByRequest(hostUser, request);
        return auctionMapper.toResponseByEntity(auctionRepository.save(auctionRoom));
    }

    @Transactional
    public AuctionResponse updateAuctionRoom(final Long roodId, AuctionRequest request) {
        request.validateDateTime();
        AuctionRoom findAuction = auctionRepository.findById(roodId)
                .orElseThrow(() -> new NoSuchElementException(roodId + AUCTION_NOT_FOUND));
        findAuction.updateAuctionInfo(request.name(), request.password(), request.limitOfEnrollment()
                ,request.biddingPrice(), request.beginAuctionDateTime(),  request.closeAuctionDateTime());
        findAuction.updateItem(request.item().getTitle(), request.item().getPrice(), request.item().getContent(), request.category());
        return auctionMapper.toResponseByEntity(findAuction);
    }

    @Transactional
    public Long deleteAuctionRoom(final Long roomId) {
        AuctionRoom findAuction = auctionRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException(roomId + AUCTION_NOT_FOUND));
        auctionRepository.delete(findAuction);
        return roomId;
    }
}
