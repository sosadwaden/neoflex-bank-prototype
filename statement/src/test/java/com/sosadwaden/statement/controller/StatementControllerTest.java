package com.sosadwaden.statement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import com.sosadwaden.statement.service.StatementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatementController.class)
@ExtendWith(MockitoExtension.class)
class StatementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatementService statementService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        LoanStatementRequestDto validRequest = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .email("myemail123@gmail.com")
                .birthdate(LocalDate.of(2000, 11, 11))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();

        LoanOfferDto loanOfferDto = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();
        when(statementService.calculationOfPossibleLoanTerms(any())).thenReturn(Collections.singletonList(loanOfferDto));

        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.singletonList(loanOfferDto))));
    }

    @Test
    public void whenValidInputForOffer_thenReturns200() throws Exception {
        LoanOfferDto validRequest = LoanOfferDto.builder()
                .requestedAmount(new BigDecimal("100000"))
                .rate(new BigDecimal("10.5"))
                .build();

        doNothing().when(statementService).offer(any());

        mockMvc.perform(post("/statement/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidInput_thenReturnsStatus400() throws Exception {
        LoanStatementRequestDto invalidRequest = new LoanStatementRequestDto();

        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
