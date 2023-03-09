package com.carrot.auction.domain.auction.domain.entity;

import com.carrot.auction.domain.auction.domain.Bid;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper=false)
public class Auction {

    @Id
    @GeneratedValue
    @Column(name="auction_id")
    private Long id;
    @Embedded
    private Bid bid;
    @Embedded private Item item;
    @Builder.Default
    @Enumerated(EnumType.STRING) private AuctionStatus auctionStatus = AuctionStatus.DRAFT;

    public void updateItem(String title, int price, String content, Category category) {
        item.changeInfo(title, price, content);
    }
}
