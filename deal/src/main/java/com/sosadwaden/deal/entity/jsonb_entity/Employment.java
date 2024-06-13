package com.sosadwaden.deal.entity.jsonb_entity;

import com.sosadwaden.deal.entity.enums.EmploymentPosition;
import com.sosadwaden.deal.entity.enums.EmploymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    EmploymentStatus status;

    @Column(name = "employer_inn")
    String employerINN;

    BigDecimal salary;

    @Enumerated(EnumType.STRING)
    EmploymentPosition position;

    Integer workExperienceTotal;

    Integer workExperienceCurrent;
}
