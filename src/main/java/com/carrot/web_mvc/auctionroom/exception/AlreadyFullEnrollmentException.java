package com.carrot.web_mvc.auctionroom.exception;


import com.carrot.web_mvc.auction.exception.AuctionBusinessException;

public class AlreadyFullEnrollmentException extends AuctionBusinessException {
    public AlreadyFullEnrollmentException(String message) {
        super(message);
    }
}
