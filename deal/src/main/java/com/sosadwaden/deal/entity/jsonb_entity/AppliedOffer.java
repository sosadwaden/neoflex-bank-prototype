package com.sosadwaden.deal.entity.jsonb_entity;

import com.sosadwaden.deal.dto.LoanOfferDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class AppliedOffer {

    BigDecimal requestedAmount;

    BigDecimal totalAmount;

    Integer term;

    BigDecimal monthlyPayment;

    BigDecimal rate;

    Boolean isInsuranceEnabled;

    Boolean isSalaryClient;
}
