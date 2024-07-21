package com.sosadwaden.gateway.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "График платежей кредита")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentScheduleElementDto {

    @Schema(description = "Номер платежа", example = "3")
    Integer number;

    @Schema(description = "Дата платежа", example = "2024-08-31")
    LocalDate date;

    @Schema(description = "Общая сумма платежа", example = "4387.14")
    BigDecimal totalPayment;

    @Schema(description = "Выплата процентов", example = "383.51")
    BigDecimal interestPayment;

    @Schema(description = "Выплата долга", example = "4003.63")
    BigDecimal debtPayment;

    @Schema(description = "Оставшийся долг", example = "88038.88")
    BigDecimal remainingDebt;
}
