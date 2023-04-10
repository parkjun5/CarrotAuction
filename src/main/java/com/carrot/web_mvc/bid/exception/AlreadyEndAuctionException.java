package com.carrot.web_mvc.bid.exception;

import com.carrot.web_mvc.auction.exception.AuctionBusinessException;

public class AlreadyEndAuctionException extends AuctionBusinessException {
    public AlreadyEndAuctionException(String message) {
        super(message);
    }
}
