package com.carrot.auction.domain.auction.exception;


public class NotEnoughBiddingPriceException extends RuntimeException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
