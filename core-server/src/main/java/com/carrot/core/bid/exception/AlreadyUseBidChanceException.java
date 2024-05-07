package com.carrot.core.bid.exception;

import com.carrot.core.auction.exception.AuctionBusinessException;

public class AlreadyUseBidChanceException extends AuctionBusinessException {
    public AlreadyUseBidChanceException(String message) {
        super(message);
    }
}
