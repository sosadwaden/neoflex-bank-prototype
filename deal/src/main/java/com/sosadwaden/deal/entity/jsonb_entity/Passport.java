package com.sosadwaden.deal.entity.jsonb_entity;

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
public class Passport {

    String series;

    String number;

    String issueBranch;

    LocalDate issueDate;
}
