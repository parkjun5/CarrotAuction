package com.carrot.parkjun5.bidrule.domain;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidRule extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bid_rule_id")
    private Long id;
    private String name;
    private String description;
    private String ruleValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
