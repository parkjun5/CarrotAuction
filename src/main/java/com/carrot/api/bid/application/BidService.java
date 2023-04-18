package com.carrot.api.bid.application;

import com.carrot.api.auction.application.AuctionService;
import com.carrot.api.auction.domain.Auction;
import com.carrot.api.auctionroom.domain.AuctionParticipation;
import com.carrot.api.auctionroom.domain.AuctionRoom;
import com.carrot.api.bid.domain.Bid;
import com.carrot.api.bid.domain.repository.BidRepository;
import com.carrot.api.bid.application.dto.BidMapper;
import com.carrot.api.bid.application.dto.BidRequest;
import com.carrot.api.bid.application.dto.BidResponse;
import com.carrot.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final BidMapper bidMapper;
    private final AuctionService auctionService;

    public BidResponse findBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("입찰이 존재하지 않습니다."));
        String bidderName = getBidderNameInParticipant(bid.getBidderId(), bid.getAuction().getAuctionRoom());
        return bidMapper.toResponseByEntities(bid.getAuction().getAuctionRoom().getName(), bid, bidderName);
    }

    @Transactional
    public BidResponse bidding(BidRequest req) {
        Auction auction = auctionService.findAuctionById(req.auctionId());

        Bid bid = bidMapper.toEntityByRequest(req);
        auction.addBid(bid);

        String bidderName = getBidderNameInParticipant(req.bidderId(), auction.getAuctionRoom());
        return bidMapper.toResponseByEntities(auction.getItem().getTitle(), bid, bidderName);
    }

    private String getBidderNameInParticipant(Long bidderId, AuctionRoom auctionRoom) {
        return auctionRoom.getAuctionParticipation()
                .stream()
                .filter(auctionParticipation -> auctionParticipation.getUser().getId().equals(bidderId))
                .findAny()
                .map(AuctionParticipation::getUser)
                .map(User::getNickname)
                .orElseThrow(() -> new NoSuchElementException("참가자 중 없는 계정입니다."));
    }

}
