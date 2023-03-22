package com.carrot.auction.domain.bid.domain.repository;

import com.carrot.auction.domain.bid.domain.entity.BidRuleBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRuleBookRepository extends JpaRepository<BidRuleBook, Long> {
}
