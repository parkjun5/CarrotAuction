package com.carrot.auction.domain.auction.exception;

import lombok.Getter;

@Getter
public class AuctionBusinessException extends RuntimeException {

    public AuctionBusinessException(String message) {
        super(message);
    }
}
