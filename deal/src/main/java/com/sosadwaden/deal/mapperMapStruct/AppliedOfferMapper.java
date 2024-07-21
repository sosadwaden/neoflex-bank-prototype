package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.statement.AppliedOfferDto;
import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppliedOfferMapper {

    AppliedOfferMapper INSTANCE = Mappers.getMapper(AppliedOfferMapper.class);

    @Mapping(source = "requestedAmount", target = "requestedAmount")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "term", target = "term")
    @Mapping(source = "monthlyPayment", target = "monthlyPayment")
    @Mapping(source = "rate", target = "rate")
    @Mapping(source = "isInsuranceEnabled", target = "isInsuranceEnabled")
    @Mapping(source = "isSalaryClient", target = "isSalaryClient")
    AppliedOffer loanOfferDtoToAppliedOffer(LoanOfferDto dto);

    AppliedOfferDto toDto(AppliedOffer appliedOffer);
}
