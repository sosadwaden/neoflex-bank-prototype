package com.sosadwaden.gateway.dto.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Schema(description = "DTO для представления паспортных данных")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassportDto {

    String series;

    String number;

    String issueBranch;

    LocalDate issueDate;
}
