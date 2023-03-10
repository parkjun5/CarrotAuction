package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.domain.AuctionValidator;
import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;
import com.carrot.auction.domain.auction.domain.repository.AuctionParticipationRepository;
import com.carrot.auction.domain.auction.dto.*;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.domain.repository.AuctionRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final AuctionParticipationRepository auctionParticipationRepository;
    private final UserService userService;
    private final AuctionMapper auctionMapper;
    private final AuctionValidator auctionValidator;
    private static final String AUCTION_NOT_FOUND = " 경매장을 찾지 못했습니다.";

    public AuctionResponse findAuctionInfoById(final Long roomId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        Set<String> nameOfParticipants = getParticipantsNicknames(auctionRoom);
        return auctionMapper.toAuctionResponseByEntity(auctionRoom, nameOfParticipants);
    }

    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest request) {
        auctionValidator.correctAuctionTime(request.beginDateTime(), request.closeDateTime());

        User hostUser = userService.findUserById(request.userId()).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionRoom auctionRoom = auctionMapper.toAuctionEntityByRequest(hostUser, request);
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(hostUser, auctionRoom);
        Set<String> nameOfParticipants = getParticipantsNicknames(auctionRoom);

        auctionParticipationRepository.save(auctionParticipation);
        return auctionMapper.toAuctionResponseByEntity(auctionRepository.save(auctionRoom), nameOfParticipants);
    }

    @Transactional
    public AuctionResponse updateAuctionRoom(final Long roomId, AuctionRequest request) {
        auctionValidator.correctAuctionTime(request.beginDateTime(), request.closeDateTime());

        AuctionRoom auctionRoom = findAuctionRoomById(roomId);

        auctionRoom.updateAuctionInfo(request.name(), request.password(), request.limitOfEnrollment(),
                request.bid().getBiddingPrice(), request.beginDateTime(), request.closeDateTime());
        auctionRoom.updateItem(request.item().getTitle(), request.item().getPrice(), request.item().getContent(), request.category());
        Set<String> nameOfParticipants = getParticipantsNicknames(auctionRoom);

        return auctionMapper.toAuctionResponseByEntity(auctionRoom, nameOfParticipants);
    }

    @Transactional
    public Long deleteAuctionRoom(final Long roomId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        AuctionParticipation auctionParticipation = auctionParticipationRepository
                .findOneByUserIdAndAuctionRoomId(auctionRoom.getHostUser().getId(), roomId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 경매장입니다."));

        auctionParticipationRepository.delete(auctionParticipation);
        auctionRepository.delete(auctionRoom);
        return roomId;
    }

    @Transactional
    public BiddingResponse updateBid(Long roomId, BiddingRequest request) {
        AuctionRoom findAuction = findAuctionRoomById(roomId);
        User bidder = findAuction.getAuctionParticipation()
                .stream()
                .map(AuctionParticipation::getUser)
                .filter(user -> user.getId().equals(request.bidderId()))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("참가자 중 없는 계정입니다."));

        auctionValidator.bidTimeBetweenAuctionTime(request.biddingTime(), findAuction.getBeginDateTime(), findAuction.getCloseDateTime());
        auctionValidator.bidPriceHigherThanMinimum(request.price(), findAuction.getBid().getBiddingPrice());

        findAuction.getBid().changeBid(bidder.getId(), request.price(), request.biddingTime());
        return auctionMapper.toBiddingResponseByEntity(findAuction, bidder);
    }

    @Transactional
    public AuctionResponse addParticipateAuctionRoom(Long roomId, Long userId) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        auctionValidator.isFullEnrollment(auctionRoom.getAuctionParticipation().size(), auctionRoom.getLimitOfEnrollment());

        User user = userService.findUserById(userId).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(user, auctionRoom);
        Set<String> nameOfParticipants = getParticipantsNicknames(auctionRoom);

        auctionParticipationRepository.save(auctionParticipation);
        return auctionMapper.toAuctionResponseByEntity(auctionRoom, nameOfParticipants);
    }

    public Page<AuctionResponse> getAuctionRoomsByPageable(Pageable pageable) {
        List<AuctionResponse> auctionResponseList = auctionRepository.findAll(pageable)
                .stream()
                .map(auctionRoom -> auctionMapper.toAuctionResponseByEntity(auctionRoom, getParticipantsNicknames(auctionRoom)))
                .toList();
        return new PageImpl<>(auctionResponseList);
    }

    private AuctionRoom findAuctionRoomById(Long roomId) {
        return auctionRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException(roomId + AUCTION_NOT_FOUND));
    }

    private Set<String> getParticipantsNicknames(AuctionRoom auctionRoom) {
        return auctionRoom.getAuctionParticipation()
                .stream()
                .map(AuctionParticipation::getUser)
                .map(User::getNickname)
                .collect(Collectors.toSet());
    }
}
