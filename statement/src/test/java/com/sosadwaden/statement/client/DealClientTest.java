package com.sosadwaden.statement.client;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DealClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DealClient dealClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendPostRequestToDealStatement() {
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

        ResponseEntity<List<LoanOfferDto>> responseEntity = new ResponseEntity<>(Collections.singletonList(loanOfferDto), HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        List<LoanOfferDto> response = dealClient.sendPostRequestToDealStatement(requestDto);

        assertEquals(1, response.size());
        assertEquals(new BigDecimal("100000"), response.get(0).getRequestedAmount());
        assertEquals(new BigDecimal("10.5"), response.get(0).getRate());
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void testSendPostRequestToDealStatementEmptyResponse() {
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

        ResponseEntity<List<LoanOfferDto>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        List<LoanOfferDto> response = dealClient.sendPostRequestToDealStatement(requestDto);

        assertEquals(0, response.size());
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        );
    }

    @Test
    public void testSendPostRequestToDealOfferSelect() {
        LoanOfferDto requestDto = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();

        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);

        dealClient.sendPostRequestToDealOfferSelect(requestDto);

        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)
        );
    }
}
