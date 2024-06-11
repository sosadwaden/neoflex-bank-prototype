package com.sosadwaden.deal.entity;

import com.sosadwaden.deal.entity.enums.CreditStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credit")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID creditId;

    BigDecimal amount;

    Integer term;

    BigDecimal monthlyPayment;

    BigDecimal rate;

    BigDecimal psk;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    JsonBinaryType paymentSchedule; // TODO тип данных под вопросом

    Boolean insuranceEnabled;

    Boolean salaryClient;

    @Enumerated(EnumType.STRING)
    CreditStatus creditStatus;
}
