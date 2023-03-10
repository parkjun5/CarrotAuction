package com.carrot.auction.domain.auction.exception;


public class NotEnoughBiddingPriceException extends AuctionBusinessException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
