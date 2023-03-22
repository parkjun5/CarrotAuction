package com.carrot.parkjun5.auction.domain;

import com.carrot.parkjun5.auction.application.dto.AuctionRequest;
import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.bid.domain.Bid;
import com.carrot.parkjun5.bidrule.domain.BidRule;
import com.carrot.parkjun5.item.domain.Category;
import com.carrot.parkjun5.item.domain.Item;
import com.carrot.parkjun5.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
