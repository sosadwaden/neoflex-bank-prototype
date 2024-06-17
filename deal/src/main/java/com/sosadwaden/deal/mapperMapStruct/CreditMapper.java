package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.CreditDto;
import com.sosadwaden.deal.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = PaymentScheduleElementMapper.class)
public interface CreditMapper {

    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);

    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "term", source = "term")
    @Mapping(target = "monthlyPayment", source = "monthlyPayment")
    @Mapping(target = "rate", source = "rate")
    @Mapping(target = "psk", source = "psk")
    @Mapping(target = "isInsuranceEnabled", source = "isInsuranceEnabled")
    @Mapping(target = "isSalaryClient", source = "isSalaryClient")
    @Mapping(target = "paymentSchedule", source = "paymentSchedule")
    Credit creditDtoToCredit(CreditDto creditDto);
}
