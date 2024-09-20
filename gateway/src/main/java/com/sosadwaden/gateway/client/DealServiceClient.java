package com.sosadwaden.gateway.client;

import com.sosadwaden.gateway.dto.FinishRegistrationRequestDto;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal-service", url = "http://deal:4456")
public interface DealServiceClient {

    @PostMapping("/deal/statement")
    List<LoanOfferDto> statement(@RequestBody LoanStatementRequestDto request);

    @PostMapping("/deal/offer/select")
    void select(@RequestBody LoanOfferDto request);

    @PostMapping("/deal/calculate/{statementId}")
    void calculate(@RequestBody FinishRegistrationRequestDto request, @PathVariable String statementId);
}
