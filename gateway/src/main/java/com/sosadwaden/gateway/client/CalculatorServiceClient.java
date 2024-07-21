package com.sosadwaden.gateway.client;

import com.sosadwaden.gateway.dto.CreditDto;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.ScoringDataDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "calculator-service", url = "http://localhost:4455")
public interface CalculatorServiceClient {

    @PostMapping("/calculator/offers")
    List<LoanOfferDto> offers(@RequestBody LoanStatementRequestDto request);

    @PostMapping("/calculator/calc")
    CreditDto calc(@RequestBody ScoringDataDto request);
}
