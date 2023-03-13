package com.carrot.auction.domain.bid.domain.repository;

import com.carrot.auction.domain.bid.domain.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
