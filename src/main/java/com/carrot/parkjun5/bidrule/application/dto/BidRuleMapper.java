package com.carrot.parkjun5.bidrule.application.dto;

import com.carrot.parkjun5.bidrule.domain.BidRule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidRuleMapper {

    BidRuleResponse toResponseByEntity(BidRule bidRule);

    BidRule toEntityByRequest(BidRuleRequest bidRequest);

}
