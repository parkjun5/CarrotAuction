package com.carrot.api.auction.exception;


public class IllegalAuctionTimeException extends AuctionBusinessException {
    public IllegalAuctionTimeException(String message) {
        super(message);
    }
}
