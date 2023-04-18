package com.carrot.api.bidrule.application;

import com.carrot.api.auction.application.AuctionService;
import com.carrot.api.auction.domain.Auction;
import com.carrot.api.auction.exception.NotEnoughBiddingPriceException;
import com.carrot.api.bid.application.dto.BidRequest;
import com.carrot.api.bid.exception.AlreadyEndAuctionException;
import com.carrot.api.bid.exception.AlreadyUseBidChanceException;
import com.carrot.api.bidrule.exception.NonExclusiveRuleTypeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BiddingRuleValidator {

    private static final Map<String, BiddingRule> BIDDING_RULES = new HashMap<>();

    private final AuctionService auctionService;

    @FunctionalInterface
    interface BiddingRule {
        void validate(BidRequest req, Auction auction, String ruleValue);
    }

    @PostConstruct
    public void setBiddingRules() {
        BIDDING_RULES.put("ChanceRule", (req, auction, ruleValue) -> {
            int chanceLimit;
            try {
                chanceLimit = Integer.parseInt(ruleValue);
            } catch (NumberFormatException exception) {
                return;
            }
            int numberOfBids = auctionService.getNumberOfBiddersBid(auction.getId(), req.bidderId());
            if (chanceLimit <= numberOfBids) {
                throw new AlreadyUseBidChanceException("이미" + chanceLimit + " 번의 입찰 기회를 전부 사용하였습니다.");
            }
        });

        BIDDING_RULES.put("TargetAmountRule", (req, auction, ruleValue) -> {
            int targetAmount;
            try {
                targetAmount = Integer.parseInt(ruleValue);
            } catch (NumberFormatException exception) {
                return;
            }
            int nowBidPrice = auctionService.findLastBiddingPrice(auction);
            if (nowBidPrice >= targetAmount) {
                auction.endAuction();
            }
        });

        BIDDING_RULES.put("TickIntervalRule", (req, auction, ruleValue) -> {
            int tickInterval;
            try {
                tickInterval = Integer.parseInt(ruleValue);
            } catch (NumberFormatException exception) {
                return;
            }
            int nowBidPrice = auctionService.findLastBiddingPrice(auction);
            int minimumPrice = Auction.getMinimumPrice(nowBidPrice, BigDecimal.valueOf(tickInterval / 100));
            if (minimumPrice >= req.biddingPrice()) {
                throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
            }
        });
        BIDDING_RULES.put("TimeLimitRule", (req, auction, ruleValue) -> {
            boolean isEndAuction = auction.getCloseDateTime().isBefore(ZonedDateTime.now());
            if (isEndAuction) {
                auction.endAuction();
            }
            throw new AlreadyEndAuctionException("이미 종료된 경매입니다.");
        });

    }

    public void validateByBiddingRule(BidRequest req, Auction auction) {
        auction.getBidRules().forEach(bidRule
                -> BIDDING_RULES.get(bidRule.getName()).validate(req, auction, bidRule.getRuleValue()));
    }

    public void checkExclusiveRuleType(List<String> biddingRules) {
        var countSameNames = biddingRules.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values();

        boolean hasExclusiveRule = countSameNames.stream().anyMatch(rule -> rule > 1);

        if (hasExclusiveRule) {
            throw new NonExclusiveRuleTypeException("같은 타입의 룰을 중복하여 설정하였습니다.");
        }
    }
}
