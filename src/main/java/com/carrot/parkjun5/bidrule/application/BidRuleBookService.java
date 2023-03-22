package com.carrot.parkjun5.bidrule.application;


import com.carrot.parkjun5.bidrule.domain.BidRuleBook;
import com.carrot.parkjun5.bidrule.domain.repository.BidRuleBookRepository;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleBookMapper;
import com.carrot.parkjun5.bidrule.application.dto.BidRuleBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
