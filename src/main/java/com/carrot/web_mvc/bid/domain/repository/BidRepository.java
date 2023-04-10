package com.carrot.web_mvc.bid.domain.repository;

import com.carrot.web_mvc.bid.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
