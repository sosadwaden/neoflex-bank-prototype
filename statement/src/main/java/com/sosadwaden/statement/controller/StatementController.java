package com.sosadwaden.statement.controller;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/statement")
@RestController
public class StatementController {

    @PostMapping()
    public ResponseEntity<List<LoanOfferDto>> calculationOfPossibleLoanTerms(LoanStatementRequestDto request) {
        return null;
    }

    @PostMapping("${application.endpoint.offer}")
    public ResponseEntity<Void> offer(LoanOfferDto request) {
        return null;
    }
}
