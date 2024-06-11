package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/deal")
@RestController
public class DealController {

    private final DealService dealService;

    @Autowired
    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("${application.endpoint.statement}")
    public ResponseEntity<List<LoanOfferDto>> statement(@RequestBody LoanStatementRequestDto request) {
        return null;
    }

    @PostMapping("${application.endpoint.offer_select}")
    public ResponseEntity<Void> select(@RequestBody LoanOfferDto request) {
        return null;
    }

    @PostMapping("${application.endpoint.calculate}/{statementId}")
    public ResponseEntity<Void> calculate(@RequestBody FinishRegistrationRequestDto request,
                                          @PathVariable String param) {
        return null;
    }
}
