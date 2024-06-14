package com.sosadwaden.deal.entity;

import com.sosadwaden.deal.entity.enums.CreditStatus;
import com.sosadwaden.deal.entity.jsonb_entity.PaymentSchedule;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID creditId;

    BigDecimal amount;

    Integer term;

    BigDecimal monthlyPayment;

    BigDecimal rate;

    BigDecimal psk;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    List<PaymentSchedule> paymentSchedule;

    Boolean isInsuranceEnabled;

    Boolean isSalaryClient;

    @Enumerated(EnumType.STRING)
    CreditStatus creditStatus;

    @Builder.Default
    @OneToMany(mappedBy = "credit")
    List<Statement> statements = new ArrayList<>();
}
