package com.carrot.parkjun5.bidrule.application;


import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bidrule.domain.repository.BidRuleRepository;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleMapper;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidRuleBookService {

    private final BidRuleRepository bidRuleRepository;
    private final BidRuleMapper bidRuleMapper;

    @Transactional
    public void setAuctionBidRules(Auction auction, List<BidRuleRequest> bidRuleRequests) {
        bidRuleRequests.stream().map(bidRuleMapper::toEntityByRequest)
                .forEach(auction::setBidRuleBook);
    }


}
