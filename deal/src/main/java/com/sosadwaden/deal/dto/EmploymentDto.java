package com.sosadwaden.deal.dto;

import com.sosadwaden.deal.entity.enums.EmploymentPosition;
import com.sosadwaden.deal.entity.enums.EmploymentStatus;
import com.sosadwaden.deal.validation.ValidEnum;
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

    @ValidEnum(enumClass = EmploymentPosition.class)
    @Schema(description = "Занимаемая должность", example = "MIDDLE_MANAGER")
    EmploymentPosition position;

    @Schema(description = "Общий стаж работы")
    Integer workExperienceTotal;

    @Schema(description = "Текущий стаж работы")
    Integer workExperienceCurrent;
}
