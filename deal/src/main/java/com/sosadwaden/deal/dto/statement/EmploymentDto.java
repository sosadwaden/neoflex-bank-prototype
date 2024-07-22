package com.sosadwaden.deal.dto.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Schema(description = "DTO для представления информации о работнике")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDto {

    String status;

    String employerINN;

    BigDecimal salary;

    String position;

    Integer workExperienceTotal;

    Integer workExperienceCurrent;
}
