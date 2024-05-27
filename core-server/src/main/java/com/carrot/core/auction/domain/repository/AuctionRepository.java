package com.carrot.core.auction.domain.repository;

import com.carrot.core.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

//    @Query("select MAX(bids.biddingPrice) from Auction ac JOIN ac.bids bids where ac.id = :auctionId")
//    Integer findMaxBiddingPriceById(@Param("auctionId") Long auctionId);

//    @Query("select count(bids.bidderId)  from Auction ac where ac.id = :auctionId")
//    int countBidByIdAndBidderId(@Param("auctionId") Long auctionId, @Param("bidderId")  Long bidderId);

    @Query("select ac from Auction ac where ac.id = :auctionId")
    Optional<Auction> findAuctionByAuctionIdFetchBidRules(@Param("auctionId") Long auctionId);

    @Query("select ac from Auction ac join fetch ac.auctionRoom where ac.id = :auctionId")
    Optional<Auction> findAuctionParticipationByAuctionId(@Param("auctionId") Long auctionId);
}
