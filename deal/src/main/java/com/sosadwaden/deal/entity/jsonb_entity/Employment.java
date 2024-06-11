package com.sosadwaden.deal.entity.jsonb_entity;

import com.sosadwaden.deal.entity.enums.EmploymentPosition;
import com.sosadwaden.deal.entity.enums.EmploymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class Employment {

    UUID employmentUUID;

    @Enumerated(EnumType.STRING)
    EmploymentStatus status;

    String employerINN;

    BigDecimal salary;

    @Enumerated(EnumType.STRING)
    EmploymentPosition position;

    Integer workExperienceTotal;

    Integer workExperienceCurrent;
}
