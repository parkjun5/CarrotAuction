package com.carrot.api.auction.domain.repository;

import com.carrot.api.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @Query("select MAX(bids.biddingPrice) from Auction ac JOIN ac.bids bids where ac.id = :auctionId")
    Integer findMaxBiddingPriceById(@Param("auctionId") Long auctionId);

    @Query("select count(bids.bidderId)  from Auction ac JOIN ac.bids bids where ac.id = :auctionId and bids.bidderId = :bidderId")
    int countBidByIdAndBidderId(@Param("auctionId") Long auctionId, @Param("bidderId")  Long bidderId);
}
