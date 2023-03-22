package com.carrot.parkjun5.auction.application;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.auction.domain.repository.AuctionRepository;
import com.carrot.parkjun5.auction.application.dto.AuctionMapper;
import com.carrot.parkjun5.auction.application.dto.AuctionRequest;
import com.carrot.parkjun5.auction.application.dto.AuctionResponse;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.auctionroom.application.AuctionRoomService;
import com.carrot.parkjun5.bidrule.domain.BidRuleBook;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleBookRequest;
import com.carrot.parkjun5.bidrule.application.BidRuleBookService;
import com.carrot.parkjun5.bidrule.application.BidRuleFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionRoomService auctionRoomService;
    private final AuctionMapper auctionMapper;
    private final BidRuleFinder bidRuleFinder;
    private final BidRuleBookService bidRuleBookService;

    @Transactional
    public AuctionResponse createAuctionToRoom(final Long auctionRoomId, AuctionRequest request) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(auctionRoomId);
        Auction auction = auctionMapper.toEntityByRequest(request);
        auctionRoom.addAuction(auction);
        List<String> codeNames = request.selectedBidRules().stream().map(BidRuleBookRequest::codeName).toList();
        bidRuleFinder.checkSelectRules(codeNames);
        Set<BidRuleBook> bidRuleBooks = bidRuleBookService.createBidRuleBook(request.selectedBidRules().toArray(new BidRuleBookRequest[]{}));
        auction.setBidRuleBooks(bidRuleBooks);

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
