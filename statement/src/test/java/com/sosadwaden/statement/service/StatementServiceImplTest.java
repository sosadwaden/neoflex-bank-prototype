package com.sosadwaden.statement.service;

import com.sosadwaden.statement.client.DealClient;
import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import com.sosadwaden.statement.service.impl.StatementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatementServiceImplTest {

    @Mock
    private DealClient dealClient;

    @InjectMocks
    private StatementServiceImpl statementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void calculationOfPossibleLoanTermsTest() {
        LoanStatementRequestDto requestDto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("myemail123@gmail.com")
                .birthdate(LocalDate.of(2000, 10, 10))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();

        LoanOfferDto offer1 = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();

        LoanOfferDto offer2 = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("50000"))
                .rate(new BigDecimal("5.0"))
                .build();

        List<LoanOfferDto> mockResponse = Arrays.asList(offer1, offer2);

        when(dealClient.sendPostRequestToDealStatement(requestDto)).thenReturn(mockResponse);

        List<LoanOfferDto> response = statementService.calculationOfPossibleLoanTerms(requestDto);

        assertEquals(mockResponse, response);
        verify(dealClient, times(1)).sendPostRequestToDealStatement(requestDto);
    }

    @Test
    public void offerTest() {
        LoanOfferDto requestDto = new LoanOfferDto();

        statementService.offer(requestDto);

        verify(dealClient, times(1)).sendPostRequestToDealOfferSelect(requestDto);
    }
}
