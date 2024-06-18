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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
    public void testCalculationOfPossibleLoanTerms() {
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

        LoanOfferDto loanOfferDto = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();

        when(dealClient.sendPostRequestToDealStatement(any())).thenReturn(Collections.singletonList(loanOfferDto));

        List<LoanOfferDto> response = statementService.calculationOfPossibleLoanTerms(requestDto);

        assertEquals(1, response.size());
        assertEquals(new BigDecimal("100000"), response.get(0).getRequestedAmount());
        assertEquals(new BigDecimal("10.5"), response.get(0).getRate());
        verify(dealClient, times(1)).sendPostRequestToDealStatement(any());
    }

    @Test
    public void testOffer() {
        LoanOfferDto requestDto = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();

        doNothing().when(dealClient).sendPostRequestToDealOfferSelect(any());

        statementService.offer(requestDto);

        verify(dealClient, times(1)).sendPostRequestToDealOfferSelect(any());
    }
}
