package com.carrot.auction.domain.bid.service;


import com.carrot.auction.domain.bid.domain.entity.BidRuleBook;
import com.carrot.auction.domain.bid.domain.repository.BidRuleBookRepository;
import com.carrot.auction.domain.bid.dto.bidrulebook.BidRuleBookMapper;
import com.carrot.auction.domain.bid.dto.bidrulebook.BidRuleBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BidRuleBookService {

    private final BidRuleBookRepository bidRuleBookRepository;
    private final BidRuleBookMapper bidRuleBookMapper;

    @Transactional
    public Set<BidRuleBook> createBidRuleBook(BidRuleBookRequest... requests) {
//        return Arrays.stream(requests).map(bidRuleBookMapper::toEntityByRequest)
//                .map(bidRuleBookRepository::save)
//                .collect(Collectors.toSet());
        return null;
    }

}
