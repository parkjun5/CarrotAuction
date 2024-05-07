package com.carrot.core.auction.exception;

import lombok.Getter;

@Getter
public class AuctionBusinessException extends RuntimeException {

    public AuctionBusinessException(String message) {
        super(message);
    }
}
