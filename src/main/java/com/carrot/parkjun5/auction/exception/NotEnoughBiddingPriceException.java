package com.carrot.parkjun5.auction.exception;


public class NotEnoughBiddingPriceException extends AuctionBusinessException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
