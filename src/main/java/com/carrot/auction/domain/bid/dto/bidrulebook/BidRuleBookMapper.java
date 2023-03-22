package com.carrot.auction.domain.bid.dto.bidrulebook;

import com.carrot.auction.domain.bid.domain.entity.BidRuleBook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidRuleBookMapper {

    BidRuleBookResponse toResponseByEntity(BidRuleBook bidRuleBook);

//    BidRuleBook toEntityByRequest(BidRuleBookRequest bidRequest);

}
