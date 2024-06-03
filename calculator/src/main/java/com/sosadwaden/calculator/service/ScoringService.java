package com.sosadwaden.calculator.service;

import com.sosadwaden.calculator.dto.LoanStatementRequestDto;
import com.sosadwaden.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;

public interface ScoringService {

    BigDecimal scoring(ScoringDataDto request);

}
