package com.carrot.core.bid.domain.repository;

import com.carrot.core.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
