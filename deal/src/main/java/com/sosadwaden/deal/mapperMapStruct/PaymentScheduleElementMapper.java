package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.PaymentScheduleElementDto;
import com.sosadwaden.deal.entity.jsonb_entity.PaymentScheduleElement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentScheduleElementMapper {

    PaymentScheduleElementMapper INSTANCE = Mappers.getMapper(PaymentScheduleElementMapper.class);

    PaymentScheduleElementDto paymentScheduleElementToPaymentScheduleElementDto(PaymentScheduleElement entity);

    PaymentScheduleElement paymentScheduleElementDtoToPaymentScheduleElement(PaymentScheduleElementDto dto);
}
