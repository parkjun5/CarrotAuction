package com.carrot.api.auction.application;

import com.carrot.api.auction.domain.Auction;
import com.carrot.api.auction.domain.repository.AuctionRepository;
import com.carrot.api.auction.application.dto.AuctionMapper;
import com.carrot.api.auction.application.dto.AuctionRequest;
import com.carrot.api.auction.application.dto.AuctionResponse;
import com.carrot.api.auction.exception.IllegalAuctionTimeException;
import com.carrot.api.auctionroom.domain.AuctionRoom;
import com.carrot.api.auctionroom.application.AuctionRoomService;
import com.carrot.api.bidrule.application.dto.BidRuleMapper;
import com.carrot.api.bidrule.application.BidRuleService;
import com.carrot.api.bidrule.application.dto.BidRuleResponse;
import com.carrot.api.bidrule.domain.BidRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionRoomService auctionRoomService;
    private final AuctionMapper auctionMapper;
    private final BidRuleMapper bidRuleMapper;
    private final BidRuleService bidRuleService;

    @Transactional
    public AuctionResponse createAuctionToRoom(final Long auctionRoomId, AuctionRequest request) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(auctionRoomId);
        Auction auction = auctionMapper.toEntityByRequest(request);
        auctionRoom.addAuction(auction);
        bidRuleService.setAuctionBidRules(auction, request.selectedBidRules());
        return getAuctionResponse(auction);
    }

    public List<AuctionResponse> getRoomAuctions(final Long auctionRoomId) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(auctionRoomId);
        return auctionRoom.getAuctions().stream()
                .map(this::getAuctionResponse)
                .toList();
    }

    public AuctionResponse findAuctionResponseById(final Long auctionId) {
        Auction auction = findAuctionById(auctionId);
        return getAuctionResponse(auction);
    }

    @Transactional
    public AuctionResponse changeAuctionByRequest(final Long auctionId, AuctionRequest request) {
        Auction auction = findAuctionById(auctionId);
        auction.changeAuctionInfo(request);
        changeAuctionTime(auction, request);
        return getAuctionResponse(auction);
    }

    @Transactional
    public Long deleteAuction(Long auctionId) {
        Auction auction = findAuctionById(auctionId);
        auctionRepository.delete(auction);
        return auctionId;
    }

    public Auction findAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException(auctionId + ": 존재하지 않는 경매번호 입니다."));
    }

    @Transactional
    public void closeAuction(Long auctionId) {
        Auction auction = findAuctionById(auctionId);
        auction.endAuction();
    }

    @Transactional
    public void openAuction(Long auctionId) {
        Auction auction = findAuctionById(auctionId);
        auction.beginAuction();
    }

    public int findLastBiddingPrice(Auction auction) {
        Integer maxBiddingPriceById = auctionRepository.findMaxBiddingPriceById(auction.getId());
        if (maxBiddingPriceById == null) {
            maxBiddingPriceById = auction.getBidStartPrice();
        }
        return maxBiddingPriceById;
    }

    public int getNumberOfBiddersBid(final Long auctionId, final Long bidderId) {
        return auctionRepository.countBidByIdAndBidderId(auctionId, bidderId);
    }

    private void changeAuctionTime(Auction auction, AuctionRequest request) {
        auction.changeBeginTime(request.beginDateTime());

        if (request.closeDateTime() != null) {
            boolean isExistTimeLimitRule = auction.getBidRules().stream()
                    .map(BidRule::getName)
                    .anyMatch(bidRuleName -> bidRuleName.equals("TimeLimitRule"));

            if (isExistTimeLimitRule) {
                throw new IllegalAuctionTimeException("종료 시간이 없는 경매입니다.");
            }
            auction.changeCloseTime(request.closeDateTime());
        }
    }

    private AuctionResponse getAuctionResponse(Auction auction) {
        Set<BidRuleResponse> bidRuleResponses = auction.getBidRules().stream()
                .map(bidRuleMapper::toResponseByEntity)
                .collect(Collectors.toSet());

        return auctionMapper.toResponseByEntities(auction, bidRuleResponses);
    }
}
