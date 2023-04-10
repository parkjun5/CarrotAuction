package com.carrot.web_mvc.bidrule.application;


import com.carrot.web_mvc.auction.domain.Auction;
import com.carrot.web_mvc.bidrule.domain.repository.BidRuleRepository;
import com.carrot.web_mvc.bidrule.application.dto.BidRuleMapper;
import com.carrot.web_mvc.bidrule.application.dto.BidRuleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidRuleService {

    private final BidRuleRepository bidRuleRepository;
    private final BidRuleMapper bidRuleMapper;

    @Transactional
    public void setAuctionBidRules(Auction auction, List<BidRuleRequest> bidRuleRequests) {
        bidRuleRequests.stream().map(bidRuleMapper::toEntityByRequest)
                .forEach(auction::setBidRuleBook);
    }


}
