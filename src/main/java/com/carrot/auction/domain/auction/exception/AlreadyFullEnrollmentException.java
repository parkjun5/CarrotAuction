package com.carrot.auction.domain.auction.exception;


public class AlreadyFullEnrollmentException extends AuctionBusinessException {
    public AlreadyFullEnrollmentException(String message) {
        super(message);
    }
}
