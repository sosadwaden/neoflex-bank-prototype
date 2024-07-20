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
public class EmploymentDto {

    String status;

    String employerINN;

    BigDecimal salary;

    String position;

    Integer workExperienceTotal;

    Integer workExperienceCurrent;
}
