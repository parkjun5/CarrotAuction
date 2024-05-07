package com.carrot.core.bid.exception;

import com.carrot.core.auction.exception.AuctionBusinessException;

public class AlreadyEndAuctionException extends AuctionBusinessException {
    public AlreadyEndAuctionException(String message) {
        super(message);
    }
}
