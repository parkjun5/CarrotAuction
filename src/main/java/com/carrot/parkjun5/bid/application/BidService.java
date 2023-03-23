package com.carrot.parkjun5.bid.application;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.auction.exception.NotEnoughBiddingPriceException;
import com.carrot.parkjun5.auctionroom.domain.AuctionParticipation;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.bid.domain.Bid;
import com.carrot.parkjun5.bid.domain.repository.BidRepository;
import com.carrot.parkjun5.bid.application.dto.BidMapper;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bid.application.dto.BidResponse;
import com.carrot.parkjun5.bid.exception.AlreadyUseBidChanceException;
import com.carrot.parkjun5.bidrule.application.rule.BiddingTargetAmountRule;
import com.carrot.parkjun5.bidrule.domain.BidRule;
import com.carrot.parkjun5.bidrule.application.rule.BiddingChanceRule;
import com.carrot.parkjun5.bidrule.application.rule.BiddingTickIntervalRule;
import com.carrot.parkjun5.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;
    private final BidMapper bidMapper;
    private final AuctionService auctionService;
    private static final int BASE_TICK_INTERVAL_NUMBER = 5;
    private static final int BASE_MAX_INT = Integer.MAX_VALUE;

    public BidResponse findBidById(Long bidId) {
        Bid bid = bidRepository.findById(bidId).orElseThrow(() -> new NoSuchElementException("존재하지 않는다."));
        String bidderName = getBidderNameInParticipant(bid.getBidderId(), bid.getAuction().getAuctionRoom());
        return bidMapper.toResponseByEntities(bid.getAuction().getAuctionRoom().getName(), bid, bidderName);
    }

    @Transactional
    public BidResponse bidding(BidRequest req) {
        Auction auction = auctionService.findAuctionById(req.auctionId());

        checkBidPrice(req.biddingPrice(), auction);
        checkBidChance(req.bidderId(), auction);
        checkTargetAmount(auction);

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

    private void checkBidPrice(int biddingPrice, Auction auction) {
        int tickInterval = auction.getBidRules().stream()
                .filter(bidRule -> BiddingTickIntervalRule.TICK_INTERVAL.name().equals(bidRule.getCode()))
                .findAny()
                .map(BidRule::getRuleValue)
                .orElse(BASE_TICK_INTERVAL_NUMBER);

        int minimumPrice = Auction.getMinimumPrice(auction.getLastBidPrice(), BigDecimal.valueOf(tickInterval / 100));
        if (minimumPrice >= biddingPrice) {
            throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
        }
    }

    private void checkBidChance(final Long bidderId, Auction auction) {
        List<String> chanceRules = Arrays.stream(BiddingChanceRule.values()).map(Enum::name).toList();
        int chanceLimit = auction.getBidRules().stream()
                .filter(bidRule -> chanceRules.contains(bidRule.getCode()))
                .findAny()
                .map(BidRule::getRuleValue)
                .orElse(BASE_MAX_INT);

        long numberOfBids = auction.getBids().stream()
                .filter(bid -> bidderId.equals(bid.getBidderId()))
                .count();

        if (chanceLimit <= numberOfBids) {
            throw new AlreadyUseBidChanceException( "이미" + chanceLimit + " 번의 입찰 기회를 전부 사용하였습니다.");
        }
    }

    private void checkTargetAmount(Auction auction) {
        int targetAmount = auction.getBidRules().stream()
                .filter(bidRule -> BiddingTargetAmountRule.TARGET_AMOUNT.name().equals(bidRule.getCode()))
                .findAny()
                .map(BidRule::getRuleValue)
                .orElse(BASE_MAX_INT);

        int nowBidPrice  = auction.getLastBidPrice();

        if (nowBidPrice >= targetAmount) {
            auction.endAuction();
        }
    }

}
