package com.sosadwaden.deal.dto.statement;

import com.sosadwaden.deal.dto.PaymentScheduleElementDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDto {

    UUID creditId;

    BigDecimal amount;

    Integer term;

    BigDecimal monthlyPayment;

    BigDecimal rate;

    BigDecimal psk;

    List<PaymentScheduleElementDto> paymentSchedule;

    Boolean isInsuranceEnabled;

    Boolean isSalaryClient;

    String creditStatus;
}
