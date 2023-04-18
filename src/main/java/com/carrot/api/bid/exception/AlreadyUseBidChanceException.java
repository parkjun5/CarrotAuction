package com.carrot.api.bid.exception;

import com.carrot.api.auction.exception.AuctionBusinessException;

public class AlreadyUseBidChanceException extends AuctionBusinessException {
    public AlreadyUseBidChanceException(String message) {
        super(message);
    }
}
