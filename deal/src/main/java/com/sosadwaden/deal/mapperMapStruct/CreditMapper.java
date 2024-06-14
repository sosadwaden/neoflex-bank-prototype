package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.CreditDto;
import com.sosadwaden.deal.dto.PaymentScheduleElementDto;
import com.sosadwaden.deal.entity.Credit;
import com.sosadwaden.deal.entity.jsonb_entity.PaymentSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
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

    default List<PaymentSchedule> mapPaymentScheduleElementDtos(List<PaymentScheduleElementDto> dtos) {
        return dtos.stream()
                .map(PaymentScheduleMapper.INSTANCE::paymentScheduleElementDtoToPaymentSchedule)
                .collect(Collectors.toList());
    }

    default List<PaymentScheduleElementDto> mapPaymentSchedulesToPaymentScheduleElementDtos(List<PaymentSchedule> schedules) {
        return schedules.stream()
                .map(PaymentScheduleMapper.INSTANCE::paymentScheduleToPaymentScheduleElementDto)
                .collect(Collectors.toList());
    }
}
