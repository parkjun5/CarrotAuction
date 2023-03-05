package com.carrot.auction.domain.auction.service;

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

    private static final String AUCTION_NOT_FOUND = " 경매장을 찾지 못했습니다.";

    public AuctionResponse findAuctionInfoById(Long auctionRoomId) {
        AuctionRoom findAuction = auctionRepository.findById(auctionRoomId).orElseThrow(() -> new NoSuchElementException(auctionRoomId + AUCTION_NOT_FOUND));
        return AuctionResponse.of(findAuction);
    }

    @Transactional
    public AuctionResponse createAuctionRoom(AuctionRequest auctionRequest) {
        User hostUser = userService.findUserById(auctionRequest.userId()).orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        auctionRequestValidate(auctionRequest);
        AuctionRoom auctionRoom = AuctionRoom.builder()
                .hostUser(hostUser)
                .name(auctionRequest.name())
                .password(auctionRequest.password())
                .item(auctionRequest.item())
                .category(auctionRequest.category())
                .beginAuctionDateTime(auctionRequest.beginAuctionDateTime())
                .closeAuctionDateTime(auctionRequest.closeAuctionDateTime())
                .limitOfEnrollment(auctionRequest.limitOfEnrollment())
                .build();
        return AuctionResponse.of(auctionRepository.save(auctionRoom));
    }

    @Transactional
    public AuctionResponse updateAuctionRoom(final Long auctionRoomId, AuctionRequest auctionRequest) {
        AuctionRoom findAuction = auctionRepository.findById(auctionRoomId).orElseThrow(() -> new NoSuchElementException(auctionRoomId + AUCTION_NOT_FOUND));
        auctionRequestValidate(auctionRequest);
        findAuction.updateAuctionInfo(auctionRequest.name(), auctionRequest.password(), auctionRequest.limitOfEnrollment(), auctionRequest.beginAuctionDateTime(), auctionRequest.closeAuctionDateTime());
        findAuction.updateItem(auctionRequest.item().getTitle(), auctionRequest.item().getPrice(), auctionRequest.item().getContent(), auctionRequest.category());
        return AuctionResponse.of(findAuction);
    }

    @Transactional
    public Long deleteAuctionRoom(final Long auctionRoomId) {
        AuctionRoom findAuction = auctionRepository.findById(auctionRoomId).orElseThrow(() -> new NoSuchElementException(auctionRoomId + AUCTION_NOT_FOUND));
        auctionRepository.delete(findAuction);
        return auctionRoomId;
    }

    private void auctionRequestValidate(AuctionRequest auctionRequest) {
        auctionRequest.validateDateTime();
    }

}
