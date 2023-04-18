package com.carrot.api.bid.domain.repository;

import com.carrot.api.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
