package com.carrot.parkjun5.auctionroom.exception;


import com.carrot.parkjun5.auction.exception.AuctionBusinessException;

public class AlreadyFullEnrollmentException extends AuctionBusinessException {
    public AlreadyFullEnrollmentException(String message) {
        super(message);
    }
}
