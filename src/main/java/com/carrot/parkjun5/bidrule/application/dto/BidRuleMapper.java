package com.carrot.parkjun5.bidrule.application.dto;

import com.carrot.parkjun5.bidrule.domain.BidRule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BidRuleMapper {
    
    @Mapping(source = "codeName", target = "code")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "auction", ignore = true)
    BidRule toEntityByRequest(BidRuleRequest bidRequest);

}
