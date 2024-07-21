package com.sosadwaden.gateway.dto.statement;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientDto {

    UUID clientId;

    String lastName;

    String firstName;

    String middleName;

    LocalDate birthdate;

    String email;

    String gender;

    String maritalStatus;

    Integer dependentAmount;

    PassportDto passport;

    EmploymentDto employmentId;

    String accountNumber;
}
