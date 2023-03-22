package com.carrot.parkjun5.bidrule.application.dto;

import com.carrot.parkjun5.bidrule.domain.BidRuleBook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidRuleBookMapper {

    BidRuleBookResponse toResponseByEntity(BidRuleBook bidRuleBook);

    BidRuleBook toEntityByRequest(BidRuleBookRequest bidRequest);

}
