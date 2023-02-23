package com.carrot.auction.domain.auction.domain.entity;

import lombok.Getter;

@Getter
public enum AuctionStatus {
    DRAFT, PUBLISHED, BEGAN_ENROLLMENT, END_ENROLLMENT
}
