package com.sosadwaden.deal.dto.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO для представления информации о клиенте")
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
