package com.carrot.auction.domain.auction.service;

import com.carrot.auction.domain.auction.domain.entity.Auction;
import com.carrot.auction.domain.auction.domain.repository.AuctionRepository;
import com.carrot.auction.domain.auction.dto.AuctionMapper;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auction.dto.AuctionResponse;
import com.carrot.auction.domain.auctionroom.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auctionroom.service.AuctionRoomService;
import com.carrot.auction.domain.bid.domain.entity.BidRuleBook;
import com.carrot.auction.domain.bid.domain.rulebook.BidRule;
import com.carrot.auction.domain.bid.domain.rulebook.BidRuleFinder;
import com.carrot.auction.domain.bid.dto.bidrulebook.BidRuleBookRequest;
import com.carrot.auction.domain.bid.dto.bidrulebook.BidRuleBookResponse;
import com.carrot.auction.domain.bid.service.BidRuleBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.carrot.auction.domain.bid.domain.entity.QBidRuleBook.bidRuleBook;

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
