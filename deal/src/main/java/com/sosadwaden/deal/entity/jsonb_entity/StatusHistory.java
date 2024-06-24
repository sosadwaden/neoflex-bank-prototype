package com.sosadwaden.deal.entity.jsonb_entity;

import com.sosadwaden.deal.entity.enums.ApplicationStatus;
import com.sosadwaden.deal.entity.enums.ChangeType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class StatusHistory {

    ApplicationStatus status;

    LocalDateTime time;

    @Enumerated(EnumType.STRING)
    ChangeType changeType;
}
