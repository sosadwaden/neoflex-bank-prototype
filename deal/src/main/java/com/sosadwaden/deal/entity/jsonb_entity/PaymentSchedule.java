package com.sosadwaden.deal.entity.jsonb_entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class PaymentSchedule {
}
