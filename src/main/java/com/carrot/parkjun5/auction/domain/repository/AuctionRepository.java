package com.carrot.parkjun5.auction.domain.repository;

import com.carrot.parkjun5.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("select MAX(bids.biddingPrice) from Auction ac join fetch ac.bids bids where ac.id = :auctionId")
    Integer findMaxBiddingPriceById(Long auctionId);

    @Query("select COUNT(bids.bidderId)  from Auction ac join fetch ac.bids bids where ac.id = :auctionId and bids.bidderId = :bidderId")
    int countBidByIdAndBidderId(Long auctionId, Long bidderId);
}
