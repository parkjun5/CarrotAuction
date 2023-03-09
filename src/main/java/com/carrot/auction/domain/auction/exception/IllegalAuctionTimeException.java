package com.carrot.auction.domain.auction.exception;


public class IllegalAuctionTimeException extends AuctionBusinessException {
    public IllegalAuctionTimeException(String message) {
        super(message);
    }
}
