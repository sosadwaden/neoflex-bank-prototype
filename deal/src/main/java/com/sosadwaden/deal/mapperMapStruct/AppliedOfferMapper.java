package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppliedOfferMapper {

    AppliedOfferMapper INSTANCE = Mappers.getMapper(AppliedOfferMapper.class);

    AppliedOffer loanOfferDtoToAppliedOffer(LoanOfferDto dto);
}
