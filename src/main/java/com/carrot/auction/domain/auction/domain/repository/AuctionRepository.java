package com.carrot.auction.domain.auction.domain.repository;

import com.carrot.auction.domain.auction.domain.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
