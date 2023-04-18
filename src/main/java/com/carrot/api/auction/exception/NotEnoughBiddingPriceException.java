package com.carrot.api.auction.exception;


public class NotEnoughBiddingPriceException extends AuctionBusinessException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
