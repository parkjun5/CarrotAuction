package com.carrot.web_mvc.auction.exception;


public class IllegalAuctionTimeException extends AuctionBusinessException {
    public IllegalAuctionTimeException(String message) {
        super(message);
    }
}
