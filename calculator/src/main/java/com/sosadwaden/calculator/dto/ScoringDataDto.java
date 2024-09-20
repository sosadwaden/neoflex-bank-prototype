package com.sosadwaden.calculator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.validation.Adult;
import com.sosadwaden.calculator.validation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoringDataDto {

    @Schema(description = "Запрашиваемая сумма кредита", example = "100000")
    @NotNull(message = "Необходимо передать сумму кредита amount")
    @Min(value = 30000, message = "Сумма кредита должна быть больше или равна 30000")
    BigDecimal amount;

    @Schema(description = "Срок кредита", example = "12")
    @NotNull(message = "Необходимо передать срок кредита term")
    @Min(value = 6, message = "Срок кредита должен быть больше или равен 6")
    Integer term;

    @Schema(description = "Имя", example = "Ivan")
    @NotNull(message = "Необходимо передать имя firstName")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Имя - от 2 до 30 латинских букв")
    String firstName;

    @Schema(description = "Фамилия", example = "Ivanov")
    @NotNull(message = "Необходимо передать фамилию lastName")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Фамилия - от 2 до 30 латинских букв")
    String lastName;

    @Schema(description = "Отчество", example = "Ivanovich")
    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Отчество - от 2 до 30 латинских букв")
    String middleName;

    @Schema(description = "Гендер", example = "FEMALE")
    @ValidEnum(enumClass = Gender.class)
    Gender gender;

    @Schema(description = "Дата рождения", example = "2000-10-10")
    @NotNull(message = "Необходимо передать день рождения birthdate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Adult
    LocalDate birthdate;

    @Schema(description = "Серия паспорта", example = "1234")
    @NotNull(message = "Необходимо передать серию паспорта passportSeries")
    @Pattern(regexp = "^\\d{4}$", message = "Серия паспорта должна содержать 4 цифры")
    String passportSeries;

    @Schema(description = "Номер паспорта", example = "123456")
    @NotNull(message = "Необходимо передать номер паспорта passportNumber")
    @Pattern(regexp = "^\\d{6}$", message = "Номер паспорта должна содержать 6 цифр")
    String passportNumber;

    @Schema(description = "Дата выдачи паспорта", example = "2014-10-10")
    LocalDate passportIssueDate;

    @Schema(description = "Отделение выдачи паспорта")
    String passportIssueBranch;

    @Schema(description = "Семейное положение", example = "DIVORCED")
    @ValidEnum(enumClass = MaritalStatus.class)
    MaritalStatus maritalStatus;

    Integer dependentAmount;

    @Schema(description = "Информация о работнике")
    EmploymentDto employment;

    String accountNumber;

    Boolean isInsuranceEnabled;

    Boolean isSalaryClient;
}

