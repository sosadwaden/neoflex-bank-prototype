package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.StatementServiceClient;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Statement controller", description = "Контроллер для заявок")
@Validated
@RequestMapping("/statement")
@RestController
public class StatementController {

    private final StatementServiceClient statementServiceClient;

    @Autowired
    public StatementController(StatementServiceClient statementServiceClient) {
        this.statementServiceClient = statementServiceClient;
    }

    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> calculationOfPossibleLoanTerms(@Valid @RequestBody LoanStatementRequestDto request) {
        List<LoanOfferDto> response = statementServiceClient.calculationOfPossibleLoanTerms(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/offer")
    public ResponseEntity<Void> offer(@RequestBody LoanOfferDto request) {
        statementServiceClient.offer(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
