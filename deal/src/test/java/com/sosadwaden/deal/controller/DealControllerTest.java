package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.EmploymentDto;
import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.enums.EmploymentPosition;
import com.sosadwaden.deal.entity.enums.EmploymentStatus;
import com.sosadwaden.deal.entity.enums.MaritalStatus;
import com.sosadwaden.deal.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DealControllerTest {

    @Mock
    private DealService dealService;

    @InjectMocks
    private DealController dealController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStatement_success() {
        LoanStatementRequestDto request = createLoanStatementRequestDto();
        List<LoanOfferDto> loanOffers = createLoanOfferDtos();

        when(dealService.statement(any(LoanStatementRequestDto.class))).thenReturn(loanOffers);

        ResponseEntity<List<LoanOfferDto>> responseEntity = dealController.statement(request);

        verify(dealService).statement(any(LoanStatementRequestDto.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(loanOffers, responseEntity.getBody());
    }

    @Test
    public void testSelect_success() {
        LoanOfferDto request = createLoanOfferDto();

        ResponseEntity<Void> responseEntity = dealController.select(request);

        verify(dealService).offerSelect(request);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testCalculate_success() {
        String statementId = "123e4567-e89b-12d3-a456-426614174000";
        FinishRegistrationRequestDto request = createFinishRegistrationRequestDto();

        ResponseEntity<Void> responseEntity = dealController.calculate(statementId, request);

        verify(dealService).calculateByStatementId(request, statementId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private LoanStatementRequestDto createLoanStatementRequestDto() {
        return LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .build();
    }

    private List<LoanOfferDto> createLoanOfferDtos() {
        LoanOfferDto offer1 = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(110000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9166.67))
                .rate(BigDecimal.valueOf(0.03))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        LoanOfferDto offer2 = LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(115000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9583.33))
                .rate(BigDecimal.valueOf(0.04))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();

        return Arrays.asList(offer1, offer2);
    }

    private LoanOfferDto createLoanOfferDto() {
        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(BigDecimal.valueOf(100000))
                .totalAmount(BigDecimal.valueOf(110000))
                .term(12)
                .monthlyPayment(BigDecimal.valueOf(9166.67))
                .rate(BigDecimal.valueOf(0.03))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }

    private FinishRegistrationRequestDto createFinishRegistrationRequestDto() {
        return FinishRegistrationRequestDto.builder()
                .employment(createEmploymentDto())
                .passportIssueDate(LocalDate.of(2000, 11, 11))
                .passportIssueBranch("123-456")
                .maritalStatus(MaritalStatus.MARRIED)
                .dependentAmount(2)
                .accountNumber("1234567890")
                .build();
    }

    private EmploymentDto createEmploymentDto() {
        return EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("123456789")
                .salary(new BigDecimal("50000"))
                .position(EmploymentPosition.MID_MANAGER)
                .workExperienceTotal(10)
                .workExperienceCurrent(2)
                .build();
    }
}
