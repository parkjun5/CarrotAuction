package com.carrot.auction.domain.bid.domain.entity;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BidRuleBook extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bid_rule_id")
    private Long id;
    private String name;
    
    // 비딩 기회 ->1, 3, 무제한 (무제한 일 경우 취소 가능) 1,3,-1
    
    // 비딩 기간 제한 -> 기간이 지나면 자동 종료로 상태 변경 0 or 1
    // 제한시간이 없는 경우 경매 등록자가 언제든 종료가능
    
    // 목표금액 -> 설정하면 이 금액을 이상의 값이 세팅되면 자동 종료 1_000_000
    
    // 비딩 금액 tick 설정 -> 기본 10퍼센트 혹은 설정 가능 tick 1 ~ 50 tick
    private int value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_room_id")
    private AuctionRoom auctionRoom;
}
