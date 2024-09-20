package com.sosadwaden.statement.service;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {

    List<LoanOfferDto> calculationOfPossibleLoanTerms(LoanStatementRequestDto request);

    void offer(LoanOfferDto request);
}
