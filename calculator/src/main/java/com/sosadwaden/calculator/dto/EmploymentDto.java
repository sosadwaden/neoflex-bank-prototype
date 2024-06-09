package com.sosadwaden.calculator.dto;

import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Position;
import com.sosadwaden.calculator.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Schema(description = "Информация о работнике")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDto {

    @ValidEnum(enumClass = EmploymentStatus.class)
    @Schema(description = "Статус трудоустройства", example = "EMPLOYED")
    EmploymentStatus employmentStatus;

    @Schema(description = "ИНН работника")
    String employerINN;

    @Schema(description = "Зарплата работника")
    BigDecimal salary;

    @ValidEnum(enumClass = Position.class)
    @Schema(description = "Занимаемая должность", example = "MIDDLE_MANAGER")
    Position position;

    @Schema(description = "Общий стаж работы")
    Integer workExperienceTotal;

    @Schema(description = "Текущий стаж работы")
    Integer workExperienceCurrent;
}
