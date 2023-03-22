package com.carrot.parkjun5.bid.application;

import com.carrot.parkjun5.auctionroom.domain.AuctionParticipation;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.auctionroom.application.AuctionRoomService;
import com.carrot.parkjun5.bid.domain.Bid;
import com.carrot.parkjun5.bid.domain.repository.BidRepository;
import com.carrot.parkjun5.bid.application.dto.BidMapper;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bid.application.dto.BidResponse;
import com.carrot.parkjun5.user.domain.User;
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
        String bidderName = getBidderNameInParticipant(bid.getBidderId(), bid.getAuction().getAuctionRoom());
        return bidMapper.toResponseByEntities(bid.getAuction().getAuctionRoom().getName(), bid, bidderName);
    }

    @Transactional
    public BidResponse bidding(BidRequest req) {
        AuctionRoom findAuctionRoom = auctionRoomService.findAuctionRoomFetchParticipation(req.roomId());
        String bidderName = getBidderNameInParticipant(req.bidderId(), findAuctionRoom);
        Bid bid = bidMapper.toEntityByRequest(req);
        //TODO AUction
        findAuctionRoom.getAuctions().get(0).addBid(bid);

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
