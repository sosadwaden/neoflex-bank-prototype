package com.sosadwaden.gateway.dto.statement;

import com.sosadwaden.gateway.dto.PaymentScheduleElementDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для представления кредита")
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
