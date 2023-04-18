package com.carrot.api.bid.exception;

import com.carrot.api.auction.exception.AuctionBusinessException;

public class AlreadyEndAuctionException extends AuctionBusinessException {
    public AlreadyEndAuctionException(String message) {
        super(message);
    }
}
