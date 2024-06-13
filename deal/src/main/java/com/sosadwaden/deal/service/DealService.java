package com.sosadwaden.deal.service;

import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;

import java.util.List;

public interface DealService {

    List<LoanOfferDto> statement(LoanStatementRequestDto request);

    void offerSelect(LoanOfferDto request);

    void calculateByStatementId(FinishRegistrationRequestDto request, String statementId);
}
