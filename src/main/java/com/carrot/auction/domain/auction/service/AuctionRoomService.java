package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.domain.AuctionValidator;
import com.carrot.auction.domain.auction.dto.*;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
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
    private final AuctionValidator auctionValidator;
    private static final String AUCTION_NOT_FOUND = " 경매장을 찾지 못했습니다.";

    public AuctionResponse findAuctionInfoById(final Long roomId) {
        return auctionMapper.toAuctionResponseByEntity(findAuctionRoomById(roomId));
    }

    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest request) {
        auctionValidator.correctAuctionTime(request.beginAuctionDateTime(), request.closeAuctionDateTime());

        User hostUser = userService.findUserById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));

        AuctionRoom auctionRoom = auctionMapper.toAuctionEntityByRequest(hostUser, request);

        return auctionMapper.toAuctionResponseByEntity(auctionRepository.save(auctionRoom));
    }

    @Transactional
    public AuctionResponse updateAuctionRoom(final Long roomId, AuctionRequest request) {
        auctionValidator.correctAuctionTime(request.beginAuctionDateTime(), request.closeAuctionDateTime());

        AuctionRoom findAuction = findAuctionRoomById(roomId);

        findAuction.updateAuctionInfo(request.name(), request.password(), request.limitOfEnrollment()
                ,request.bid().getBiddingPrice(), request.beginAuctionDateTime(),  request.closeAuctionDateTime());

        findAuction.updateItem(request.item().getTitle(), request.item().getPrice(), request.item().getContent(), request.category());

        return auctionMapper.toAuctionResponseByEntity(findAuction);
    }

    @Transactional
    public Long deleteAuctionRoom(final Long roomId) {
        auctionRepository.delete(findAuctionRoomById(roomId));
        return roomId;
    }

    @Transactional
    public BiddingResponse updateBid(Long roomId, BiddingRequest request) {
        AuctionRoom findAuction = findAuctionRoomById(roomId);

        User bidder = findAuction.getParticipants().stream()
                .filter(user -> user.getId().equals(request.bidderId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("참가자 중 없는 계정입니다."));

        auctionValidator.bidTimeBetweenAuctionTime(request.biddingTime(), findAuction.getBeginAuctionDateTime(), findAuction.getCloseAuctionDateTime());
        auctionValidator.bidPriceHigherThanMinimum(request.price(), findAuction.getBid().getBiddingPrice());

        findAuction.getBid().changeBid(bidder.getId(), request.price(), request.biddingTime());

        return auctionMapper.toBiddingResponseByEntity(findAuction, bidder);
    }

    private AuctionRoom findAuctionRoomById(Long roomId) {
        return auctionRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException(roomId + AUCTION_NOT_FOUND));
    }
}
