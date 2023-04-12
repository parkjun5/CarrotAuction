package com.carrot.parkjun5.bid.exception;

import com.carrot.parkjun5.auction.exception.AuctionBusinessException;

public class AlreadyUseBidChanceException extends AuctionBusinessException {
    public AlreadyUseBidChanceException(String message) {
        super(message);
    }
}
