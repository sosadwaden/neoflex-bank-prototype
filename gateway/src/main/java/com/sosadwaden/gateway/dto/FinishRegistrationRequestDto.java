package com.sosadwaden.gateway.dto;

import com.sosadwaden.gateway.dto.statement.EmploymentDto;
import com.sosadwaden.gateway.enums.Gender;
import com.sosadwaden.gateway.enums.MaritalStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinishRegistrationRequestDto {

    Gender gender;

    MaritalStatus maritalStatus;

    Integer dependentAmount;

    LocalDate passportIssueDate;

    String passportIssueBranch;

    EmploymentDto employment;

    String accountNumber;
}
