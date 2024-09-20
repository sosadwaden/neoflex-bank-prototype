package com.sosadwaden.calculator.service;

import com.sosadwaden.calculator.dto.CreditDto;
import com.sosadwaden.calculator.dto.LoanOfferDto;
import com.sosadwaden.calculator.dto.LoanStatementRequestDto;
import com.sosadwaden.calculator.dto.ScoringDataDto;

import java.util.List;

public interface CalculatorService {

    List<LoanOfferDto> generateOffers(LoanStatementRequestDto request);

    CreditDto generateCreditDto(ScoringDataDto request);
}
