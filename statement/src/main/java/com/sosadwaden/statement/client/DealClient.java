package com.sosadwaden.statement.client;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal-service", url = "http://localhost:4456/deal")
public interface DealClient {

    @PostMapping("/statement")
    List<LoanOfferDto> sendPostRequestToDealStatement(@RequestBody LoanStatementRequestDto request);

    @PostMapping("/offer/select")
    void sendPostRequestToDealOfferSelect(LoanOfferDto request);
}
