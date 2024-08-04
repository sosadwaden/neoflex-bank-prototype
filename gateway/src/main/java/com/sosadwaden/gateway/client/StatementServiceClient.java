package com.sosadwaden.gateway.client;

import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "statement-service", url = "http://localhost:4457")
public interface StatementServiceClient {

    @PostMapping("/statement")
    List<LoanOfferDto> calculationOfPossibleLoanTerms(@RequestBody LoanStatementRequestDto request);

    @PostMapping("/statement/offer")
    void offer(@RequestBody LoanOfferDto request);
}
