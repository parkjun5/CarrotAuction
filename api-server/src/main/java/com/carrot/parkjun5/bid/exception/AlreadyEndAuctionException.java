package com.carrot.parkjun5.bid.exception;

import com.carrot.parkjun5.auction.exception.AuctionBusinessException;

public class AlreadyEndAuctionException extends AuctionBusinessException {
    public AlreadyEndAuctionException(String message) {
        super(message);
    }
}
