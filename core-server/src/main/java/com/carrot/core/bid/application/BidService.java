package com.carrot.core.bid.application;

import com.carrot.core.auction.application.AuctionService;
import com.carrot.core.auction.domain.Auction;
import com.carrot.core.auctionroom.domain.AuctionParticipation;
import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.bid.application.dto.BidRequest;
import com.carrot.core.bid.application.dto.BidResponse;
import com.carrot.core.bid.domain.Bid;
import com.carrot.core.bid.domain.repository.BidRepository;
import com.carrot.core.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionService auctionService;

    public BidResponse findBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("입찰이 존재하지 않습니다."));
//        String bidderName = getBidderNameInParticipant(bid.getBidderId(), bid.getAuction().getAuctionRoom());
//        return BidResponse.from(bid,bid.getAuction().getAuctionRoom().getName(), bidderName, bid.getBidderId());
        return null;
    }

    @Transactional
    public BidResponse bidding(BidRequest request) {
        Auction auction = auctionService.findAuctionById(request.auctionId());

        Bid bid = Bid.of(request, request.auctionId());
        bidRepository.save(bid);

        String bidderName = getBidderNameInParticipant(request.bidderId(), auction.getAuctionRoom());
        return BidResponse.from(bid, auction.getItem().getTitle(), bidderName, bid.getBidderId());
    }

    @Transactional
    public Long newBidding(long auctionId, int biddingPrice, ZonedDateTime biddingTime, long bidderId) {
        Bid bid = Bid.of(auctionId, biddingPrice, biddingTime, bidderId);
        bidRepository.save(bid);
        return bid.getId();
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

    public int findLatestBidPrice(Long auctionId, int defaultBidPrice) {
        return bidRepository.findLatestBidPriceByAuctionId(auctionId)
                .orElse(defaultBidPrice);
    }
}
