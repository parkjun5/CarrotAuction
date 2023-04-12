package com.carrot.parkjun5.bid.domain.repository;

import com.carrot.parkjun5.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
