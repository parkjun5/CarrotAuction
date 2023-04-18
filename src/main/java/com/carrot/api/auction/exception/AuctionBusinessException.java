package com.carrot.api.auction.exception;

import lombok.Getter;

@Getter
public class AuctionBusinessException extends RuntimeException {

    public AuctionBusinessException(String message) {
        super(message);
    }
}
