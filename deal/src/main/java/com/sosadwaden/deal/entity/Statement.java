package com.sosadwaden.deal.entity;

import com.sosadwaden.deal.entity.enums.ApplicationStatus;
import com.sosadwaden.deal.entity.jsonb_entity.AppliedOffer;
import com.sosadwaden.deal.entity.jsonb_entity.StatusHistory;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "statement")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Statement {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID statementId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;
//
//    @ManyToOne
//    @JoinColumn(name = "credit_id")
//    Credit credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", insertable = false, updatable = false)
    ApplicationStatus status;

    LocalDateTime creationDate;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    AppliedOffer appliedOffer;

    LocalDateTime signDate;

    String sesCode;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    StatusHistory statusHistory;
}
