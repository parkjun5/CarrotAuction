package com.carrot.web_mvc.auction.exception;


public class NotEnoughBiddingPriceException extends AuctionBusinessException {
    public NotEnoughBiddingPriceException(String message) {
        super(message);
    }
}
