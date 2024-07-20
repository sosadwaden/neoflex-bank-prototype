package com.sosadwaden.deal.dto.statement;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
