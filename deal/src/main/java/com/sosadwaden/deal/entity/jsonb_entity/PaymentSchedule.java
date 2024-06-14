package com.sosadwaden.deal.entity.jsonb_entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class PaymentSchedule {

    Integer number;

    LocalDate date;

    BigDecimal totalPayment;

    BigDecimal interestPayment;

    BigDecimal debtPayment;

    BigDecimal remainingDebt;
}
