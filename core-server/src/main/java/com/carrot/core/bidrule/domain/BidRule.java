package com.carrot.core.bidrule.domain;

import com.carrot.core.auction.domain.Auction;
import com.carrot.core.bidrule.application.dto.BidRuleRequest;
import com.carrot.core.common.domain.BaseEntity;
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

    public static BidRule of(BidRuleRequest request) {
        BidRule bidRule = new BidRule();
        bidRule.name = request.name();
        bidRule.ruleValue = request.ruleValue();
        bidRule.description = request.description();
        return bidRule;
    }
}
