package com.carrot.auction.domain.bid.exception;

import com.carrot.auction.domain.auction.exception.AuctionBusinessException;

public class DuplicatedBidRuleTypeException extends AuctionBusinessException {
    public DuplicatedBidRuleTypeException(String message) {
        super(message);
    }
}
