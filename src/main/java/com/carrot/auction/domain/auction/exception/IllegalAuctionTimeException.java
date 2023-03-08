package com.carrot.auction.domain.auction.exception;


public class IllegalAuctionTimeException extends RuntimeException {
    public IllegalAuctionTimeException(String message) {
        super(message);
    }
}
