package com.carrot.parkjun5.auction.application;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.auction.domain.repository.AuctionRepository;
import com.carrot.parkjun5.auction.application.dto.AuctionMapper;
import com.carrot.parkjun5.auction.application.dto.AuctionRequest;
import com.carrot.parkjun5.auction.application.dto.AuctionResponse;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.auctionroom.application.AuctionRoomService;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleRequest;
import com.carrot.parkjun5.bidrule.application.BidRuleService;
import com.carrot.parkjun5.bidrule.application.BidRuleFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionRoomService auctionRoomService;
    private final AuctionMapper auctionMapper;
    private final BidRuleFinder bidRuleFinder;
    private final BidRuleService bidRuleService;

    @Transactional
    public AuctionResponse createAuctionToRoom(final Long auctionRoomId, AuctionRequest request) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(auctionRoomId);
        Auction auction = auctionMapper.toEntityByRequest(request);
        auctionRoom.addAuction(auction);

        //TODO VALIDATE 어노테이션으로 TEST 작성
        List<String> codeNames = request.selectedBidRules().stream().map(BidRuleRequest::codeName).toList();
        bidRuleFinder.checkSelectRules(codeNames);

        bidRuleService.setAuctionBidRules(auction, request.selectedBidRules());

        return auctionMapper.toResponseByEntity(auction);
    }

    public List<AuctionResponse> getRoomAuctions(final Long auctionRoomId) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(auctionRoomId);
        return auctionRoom.getAuctions().stream()
                .map(auctionMapper::toResponseByEntity)
                .toList();
    }

    @Transactional
    public AuctionResponse changeAuctionByRequest(final Long auctionId, AuctionRequest request) {
        Auction auction = findAuctionById(auctionId);
        auction.changeAuctionInfo(request);
        changeAuctionTime(auction, request);
        return auctionMapper.toResponseByEntity(auction);
    }

    @Transactional
    public Long deleteAuction(Long auctionId) {
        Auction auction = findAuctionById(auctionId);
        auctionRepository.delete(auction);
        return auctionId;
    }

    private void changeAuctionTime(Auction auction, AuctionRequest request) {
        // TODO RULE 처리도 필요함
        if (request.beginDateTime() != null) {
            auction.changeBeginTime(request.beginDateTime());
        }
        if (request.closeDateTime() != null) {
            auction.changeCloseTime(request.closeDateTime());
        }
    }

    private Auction findAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NoSuchElementException(auctionId + ": 존재하지 않는 경매번호 입니다."));
    }

}
