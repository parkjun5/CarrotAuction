package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.domain.AuctionStatus;
import com.carrot.auction.domain.auction.dto.AuctionRequest;
import com.carrot.auction.domain.auctionroom.domain.entity.AuctionRoom;
import com.carrot.auction.domain.bid.domain.entity.Bid;
import com.carrot.auction.domain.bid.domain.entity.BidRuleBook;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.global.domain.BaseEntity;
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
    @JoinColumn(name = "bid_rule_book_id")
    @Builder.Default
    private Set<BidRuleBook> bidRuleBooks= new HashSet<>();
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
    private int biddingChance; // null

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

    public void setBidRuleBooks(Set<BidRuleBook> bidRuleBooks) {
        for (BidRuleBook bidRuleBook : bidRuleBooks) {
            bidRuleBook.setAuction(this);
        }
        this.bidRuleBooks = bidRuleBooks;
    }
}
