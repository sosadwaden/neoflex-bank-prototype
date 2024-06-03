package com.sosadwaden.calculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Кредит")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDto {

    @Schema(description = "Запрашиваемая сумма кредита", example = "100000")
    BigDecimal amount;

    @Schema(description = "Срок кредита", example = "12")
    Integer term;

    @Schema(description = "Ежемесячный платёж", example = "4578.43")
    BigDecimal monthlyPayment;

    @Schema(description = "Процентная ставка по кредиту", example = "0.03")
    BigDecimal rate;

    @Schema(description = "Полная стоимость кредита", example = "105032.23")
    BigDecimal psk;

    @Schema(description = "Включена ли страховка")
    boolean isInsuranceEnabled;

    @Schema(description = "Зарплатный клиент")
    boolean isSalaryClient;

    @Schema(description = "График платежей")
    List<PaymentScheduleElementDto> paymentSchedule;
}
