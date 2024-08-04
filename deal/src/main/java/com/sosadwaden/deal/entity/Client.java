package com.sosadwaden.deal.entity;

import com.sosadwaden.deal.entity.enums.Gender;
import com.sosadwaden.deal.entity.enums.MaritalStatus;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    UUID clientId;

    String lastName;

    String firstName;

    String middleName;

    LocalDate birthdate;

    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Enumerated(EnumType.STRING)
    MaritalStatus maritalStatus;

    Integer dependentAmount;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Passport passportId;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    Employment employmentId;

    String accountNumber;

    @OneToOne(mappedBy = "client")
    Statement statement;
}
