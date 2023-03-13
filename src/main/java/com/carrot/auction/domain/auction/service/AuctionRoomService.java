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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionRoomService {

    private final AuctionRoomRepository auctionRepository;
    private final AuctionParticipationRepository auctionParticipationRepository;
    private final UserService userService;
    private final AuctionMapper auctionMapper;
    private final AuctionValidator auctionValidator;
    private static final String AUCTION_NOT_FOUND = "의 경매장을 찾지 못했습니다.";

    public AuctionResponse findAuctionInfoById(final Long roomId) {
        final AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        final Set<String> nameOfParticipants = auctionRoom.getParticipantsNicknames();
        return auctionMapper.toAuctionResponseByEntity(auctionRoom, nameOfParticipants);
    }

    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest request) {
        User hostUser = userService.findUserById(request.userId()).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionRoom auctionRoom = auctionMapper.toAuctionEntityByRequest(hostUser, request);
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(hostUser, auctionRoom);
        Set<String> nameOfParticipants = auctionRoom.getParticipantsNicknames();

        auctionParticipationRepository.save(auctionParticipation);
        return auctionMapper.toAuctionResponseByEntity(auctionRepository.save(auctionRoom), nameOfParticipants);
    }

    @Transactional
    public AuctionResponse updateAuctionRoom(final Long roomId, AuctionRequest request) {
        AuctionRoom auctionRoom = findAuctionRoomById(roomId);
        auctionRoom.updateAuctionInfo(request.name(), request.password(), request.limitOfEnrollment(),
                request.bid().getBiddingPrice(), request.beginDateTime(), request.closeDateTime());
        auctionRoom.updateItem(request.item().getTitle(), request.item().getPrice(), request.item().getContent(), request.category());
        final Set<String> nameOfParticipants = auctionRoom.getParticipantsNicknames();

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
        AuctionRoom findAuctionRoom = findAuctionRoomFetchParticipation(roomId);
        User bidder = findAuctionRoom.getAuctionParticipation()
                .stream()
                .filter(auctionParticipation -> auctionParticipation.getUser().getId().equals(request.bidderId()))
                .map(AuctionParticipation::getUser)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("참가자 중 없는 계정입니다."));

        auctionValidator.bidTimeBetweenAuctionTime(request.biddingTime(), findAuctionRoom.getBeginDateTime(), findAuctionRoom.getCloseDateTime());
        auctionValidator.bidPriceHigherThanMinimum(request.price(), findAuctionRoom.getBid().getBiddingPrice());

        findAuctionRoom.getBid().changeBid(bidder.getId(), request.price(), request.biddingTime());
        return auctionMapper.toBiddingResponseByEntity(findAuctionRoom, bidder);
    }

    @Transactional
    public AuctionResponse addParticipateAuctionRoom(Long roomId, Long userId) {
        AuctionRoom auctionRoom = findAuctionRoomFetchParticipation(roomId);
        auctionValidator.isFullEnrollment(auctionRoom.getAuctionParticipation().size(), auctionRoom.getLimitOfEnrollment());

        User user = userService.findUserById(userId).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        AuctionParticipation auctionParticipation = AuctionParticipation.createAuctionParticipation(user, auctionRoom);
        final Set<String> nameOfParticipants = auctionRoom.getParticipantsNicknames();

        auctionParticipationRepository.save(auctionParticipation);
        return auctionMapper.toAuctionResponseByEntity(auctionRoom, nameOfParticipants);
    }

    public Page<AuctionResponse> getAuctionRoomsByPageable(Pageable pageable) {
        final List<AuctionResponse> auctionResponseList = auctionRepository.findAll(pageable)
                .stream()
                .map(auctionRoom -> auctionMapper.toAuctionResponseByEntity(auctionRoom, auctionRoom.getParticipantsNicknames()))
                .toList();

        return new PageImpl<>(auctionResponseList);
    }

    private AuctionRoom findAuctionRoomById(Long roomId) {
        return auctionRepository.findById(roomId).orElseThrow(() -> new NoSuchElementException("ID: " + roomId + AUCTION_NOT_FOUND));
    }

    private AuctionRoom findAuctionRoomFetchParticipation(Long roomId) {
        AuctionRoom findAuctionRoom = auctionRepository.findByIdFetchParticipation(roomId);
        Assert.notNull(findAuctionRoom,"경매장 아이디: " + roomId + AUCTION_NOT_FOUND);
        return findAuctionRoom;
    }
}
