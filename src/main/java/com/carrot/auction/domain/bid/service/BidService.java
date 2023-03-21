package com.carrot.auction.domain.bid.service;

import com.carrot.auction.domain.auction.domain.entity.AuctionParticipation;
import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.domain.auction.service.AuctionRoomService;
import com.carrot.auction.domain.bid.domain.entity.Bid;
import com.carrot.auction.domain.bid.domain.repository.BidRepository;
import com.carrot.auction.domain.bid.dto.BidMapper;
import com.carrot.auction.domain.bid.dto.BidRequest;
import com.carrot.auction.domain.bid.dto.BidResponse;
import com.carrot.auction.domain.user.domain.entity.User;
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
    private final AuctionRoomService auctionRoomService;

    public BidResponse findBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("존재하지 않는다."));
        String bidderName = getBidderNameInParticipant(bid.getBidderId(), bid.getAuctionRoom());
        return bidMapper.toResponseByEntities(bid.getAuctionRoom().getName(), bid, bidderName);
    }

    @Transactional
    public BidResponse bidding(BidRequest req) {
        AuctionRoom findAuctionRoom = auctionRoomService.findAuctionRoomFetchParticipation(req.roomId());
        String bidderName = getBidderNameInParticipant(req.bidderId(), findAuctionRoom);
        Bid bid = bidMapper.toEntityByRequest(req);
        findAuctionRoom.addBid(bid);

        return bidMapper.toResponseByEntities(findAuctionRoom.getName(), bid, bidderName);
    }

    private String getBidderNameInParticipant(Long bidderId, AuctionRoom findAuctionRoom) {
        return findAuctionRoom.getAuctionParticipation()
                .stream()
                .filter(auctionParticipation -> auctionParticipation.getUser().getId().equals(bidderId))
                .findAny()
                .map(AuctionParticipation::getUser)
                .map(User::getNickname)
                .orElseThrow(() -> new NoSuchElementException("참가자 중 없는 계정입니다."));
    }
}
