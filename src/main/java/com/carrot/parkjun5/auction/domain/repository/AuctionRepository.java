package com.carrot.parkjun5.auction.domain.repository;

import com.carrot.parkjun5.auction.domain.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
