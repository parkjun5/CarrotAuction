package com.carrot.parkjun5.bidrule.application.dto;

import com.carrot.parkjun5.bidrule.domain.BidRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidRuleMapper {

    BidRuleResponse toResponseByEntity(BidRule bidRule);

    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auction", ignore = true)
    BidRule toEntityByRequest(BidRuleRequest bidRequest);

}
