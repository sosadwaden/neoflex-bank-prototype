package com.sosadwaden.deal.entity.jsonb_entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class Passport {

    UUID passportUUID;

    String series;

    String number;

    String issueBranch;

    LocalDate issueDate;
}
