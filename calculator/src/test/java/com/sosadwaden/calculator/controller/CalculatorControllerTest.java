package com.sosadwaden.calculator.controller;

import com.sosadwaden.calculator.dto.*;
import com.sosadwaden.calculator.enums.EmploymentPosition;
import com.sosadwaden.calculator.enums.EmploymentStatus;
import com.sosadwaden.calculator.enums.Gender;
import com.sosadwaden.calculator.enums.MaritalStatus;
import com.sosadwaden.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorControllerTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private CalculatorController calculatorController;

    @Test
    void offersTest() {
        LoanStatementRequestDto requestDto = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("myemail123@gmail.com")
                .birthdate(LocalDate.of(2000, 10, 10))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();
        when(calculatorService.generateOffers(requestDto)).thenReturn(Collections.singletonList(new LoanOfferDto()));

        ResponseEntity<List<LoanOfferDto>> responseEntity = calculatorController.offers(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    void calcTest() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("1234567890")
                .salary(BigDecimal.valueOf(60000))
                .employmentPosition(EmploymentPosition.MID_MANAGER)
                .workExperienceTotal(24)
                .workExperienceCurrent(24)
                .build();

        ScoringDataDto request = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .gender(Gender.MALE)
                .birthdate(LocalDate.of(2000, 10, 10))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.of(2014, 10, 10))
                .passportIssueBranch("Branch")
                .maritalStatus(MaritalStatus.DIVORCED)
                .dependentAmount(1)
                .employment(employmentDto)
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        when(calculatorService.generateCreditDto(request)).thenReturn(new CreditDto());

        ResponseEntity<CreditDto> responseEntity = calculatorController.calc(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
