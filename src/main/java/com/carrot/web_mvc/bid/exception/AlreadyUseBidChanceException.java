package com.carrot.web_mvc.bid.exception;

import com.carrot.web_mvc.auction.exception.AuctionBusinessException;

public class AlreadyUseBidChanceException extends AuctionBusinessException {
    public AlreadyUseBidChanceException(String message) {
        super(message);
    }
}
