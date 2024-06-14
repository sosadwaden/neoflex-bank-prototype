package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.PaymentScheduleElementDto;
import com.sosadwaden.deal.entity.jsonb_entity.PaymentSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentScheduleMapper {

    PaymentScheduleMapper INSTANCE = Mappers.getMapper(PaymentScheduleMapper.class);

    @Mapping(target = "number", source = "number")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "totalPayment", source = "totalPayment")
    @Mapping(target = "interestPayment", source = "interestPayment")
    @Mapping(target = "debtPayment", source = "debtPayment")
    @Mapping(target = "remainingDebt", source = "remainingDebt")
    PaymentSchedule paymentScheduleElementDtoToPaymentSchedule(PaymentScheduleElementDto dto);

    @Mapping(target = "number", source = "number")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "totalPayment", source = "totalPayment")
    @Mapping(target = "interestPayment", source = "interestPayment")
    @Mapping(target = "debtPayment", source = "debtPayment")
    @Mapping(target = "remainingDebt", source = "remainingDebt")
    PaymentScheduleElementDto paymentScheduleToPaymentScheduleElementDto(PaymentSchedule schedule);
}
