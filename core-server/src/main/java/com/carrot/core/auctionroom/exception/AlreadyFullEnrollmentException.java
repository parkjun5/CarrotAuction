package com.carrot.core.auctionroom.exception;


import com.carrot.core.auction.exception.AuctionBusinessException;

public class AlreadyFullEnrollmentException extends AuctionBusinessException {
    public AlreadyFullEnrollmentException(String message) {
        super(message);
    }
}
