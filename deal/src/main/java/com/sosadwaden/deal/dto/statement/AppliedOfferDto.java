package com.sosadwaden.deal.dto.statement;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppliedOfferDto {

    BigDecimal requestedAmount;

    BigDecimal totalAmount;

    Integer term;

    BigDecimal monthlyPayment;

    BigDecimal rate;

    Boolean isInsuranceEnabled;

    Boolean isSalaryClient;
}
