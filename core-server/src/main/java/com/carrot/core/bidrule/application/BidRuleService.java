package com.carrot.core.bidrule.application;


import com.carrot.core.auction.domain.Auction;
import com.carrot.core.bidrule.domain.BidRule;
import com.carrot.core.bidrule.domain.repository.BidRuleRepository;
import com.carrot.core.bidrule.application.dto.BidRuleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidRuleService {

    private final BidRuleRepository bidRuleRepository;

    @Transactional
    public void setAuctionBidRules(Auction auction, List<BidRuleRequest> bidRuleRequests) {
        bidRuleRequests.stream().map(BidRule::of)
                .forEach(auction::setBidRuleBook);
    }


}
