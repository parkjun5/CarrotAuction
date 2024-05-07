package com.carrot.core.auction.exception;


public class NotEnoughBiddingPriceException extends AuctionBusinessException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
