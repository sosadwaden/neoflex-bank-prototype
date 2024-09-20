package com.sosadwaden.statement.service.impl;

import com.sosadwaden.statement.client.DealClient;
import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import com.sosadwaden.statement.service.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {

    private final DealClient dealClient;

    @Override
    public List<LoanOfferDto> calculationOfPossibleLoanTerms(LoanStatementRequestDto request) {
        return dealClient.sendPostRequestToDealStatement(request);
    }

    @Override
    public void offer(LoanOfferDto request) {
        dealClient.sendPostRequestToDealOfferSelect(request);
    }
}
