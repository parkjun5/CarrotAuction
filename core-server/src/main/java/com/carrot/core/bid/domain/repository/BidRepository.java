package com.carrot.core.bid.domain.repository;

import com.carrot.core.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query("SELECT bd.biddingPrice FROM Bid bd WHERE bd.auctionId = :auctionId ORDER BY bd.id DESC")
    Optional<Integer> findLatestBidPriceByAuctionId(@Param("auctionId") Long auctionId);
}
