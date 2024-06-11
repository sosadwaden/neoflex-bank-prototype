package com.sosadwaden.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Кредитное предложение")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanOfferDto {

    @Schema(description = "UUID", accessMode = Schema.AccessMode.READ_ONLY)
    UUID statementId;

    @Schema(description = "Запрашиваемая сумма", example = "100000")
    BigDecimal requestedAmount;

    @Schema(description = "Общая сумма")
    BigDecimal totalAmount;

    @Schema(description = "Срок кредита", example = "12")
    Integer term;

    @Schema(description = "Ежемесячный платёж", example = "4578.43")
    BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка по кредиту", example = "0.03")
    BigDecimal rate;

    @Schema(description = "Включена ли страховка", example = "105032.23")
    Boolean isInsuranceEnabled;

    @Schema(description = "Зарплатный клиент")
    Boolean isSalaryClient;
}
