package com.carrot.core.auction.domain;

import com.carrot.core.auction.application.dto.AuctionRequest;
import com.carrot.core.auctionroom.domain.AuctionRoom;
import com.carrot.core.bid.domain.Bid;
import com.carrot.core.bidrule.domain.BidRule;
import com.carrot.core.common.domain.BaseEntity;
import com.carrot.core.item.domain.Category;
import com.carrot.core.item.domain.Item;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auction extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "auction_id")
    private Long id;
    private int bidStartPrice;
    @Enumerated(EnumType.STRING) private Category category;
    @Embedded private Item item;
    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_rule_id")
    @Builder.Default
    private Set<BidRule> bidRules = new HashSet<>();
    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_id")
    @Builder.Default
    private List<Bid> bids = new ArrayList<>();
    @Builder.Default
    @Enumerated(EnumType.STRING) private AuctionStatus auctionStatus = AuctionStatus.DRAFT;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "auction_room_id")
    private AuctionRoom auctionRoom;
    private ZonedDateTime beginDateTime;
    private ZonedDateTime closeDateTime;

    public void addBid(Bid bid) {
        this.bids.add(bid);
        bid.setAuction(this);
    }

    public static Auction of(AuctionRequest request) {
        Auction auction = new Auction();
        auction.bidStartPrice = request.bidStartPrice();
        auction.item = request.item();
        auction.category = request.category();
        auction.beginDateTime = request.beginDateTime();
        auction.closeDateTime = request.closeDateTime();
        auction.bidRules = request.selectedBidRules()
                                  .stream()
                                  .map(BidRule::of)
                                  .collect(Collectors.toSet());
        return auction;
    }

    public static int getMinimumPrice(int existingPrice, BigDecimal minBiddingPercent) {
        return existingPrice + minBiddingPercent.multiply(BigDecimal.valueOf(existingPrice)).intValue();
    }

    public void changeAuctionInfo(AuctionRequest auctionRequest) {
        this.bidStartPrice = auctionRequest.bidStartPrice();
        Item requestItem = auctionRequest.item();
        this.item.changeInfo(requestItem.getTitle(), requestItem.getPrice(), requestItem.getContent());
        this.category = auctionRequest.category();
    }

    public void changeBeginTime(ZonedDateTime beginDateTime) {
        this.beginDateTime = beginDateTime;
    }
    public void changeCloseTime(ZonedDateTime closeDateTime) {
        this.closeDateTime = closeDateTime;
    }
    public void setAuctionRoom(AuctionRoom auctionRoom) {
        this.auctionRoom = auctionRoom;
    }

    public void setBidRuleBook(BidRule bidRule) {
        bidRule.setAuction(this);
        this.bidRules.add(bidRule);
    }

    public void beginAuction() {
        this.auctionStatus = AuctionStatus.BEGAN_ENROLLMENT;
    }

    public void endAuction() {
        this.auctionStatus = AuctionStatus.END_ENROLLMENT;
    }

}
