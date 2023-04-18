package com.carrot.api.auctionroom.exception;


import com.carrot.api.auction.exception.AuctionBusinessException;

public class AlreadyFullEnrollmentException extends AuctionBusinessException {
    public AlreadyFullEnrollmentException(String message) {
        super(message);
    }
}
