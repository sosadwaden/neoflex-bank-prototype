package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.CalculatorServiceClient;
import com.sosadwaden.gateway.dto.CreditDto;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.ScoringDataDto;
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

@Tag(name = "Calculator controller", description = "Контроллер для путей /calculator")
@Validated
@RequestMapping("/calculator")
@RestController
public class CalculatorController {

    private final CalculatorServiceClient calculatorServiceClient;

    @Autowired
    public CalculatorController(CalculatorServiceClient calculatorServiceClient) {
        this.calculatorServiceClient = calculatorServiceClient;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> offers(@Valid @RequestBody LoanStatementRequestDto request) {
        List<LoanOfferDto> response = calculatorServiceClient.offers(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calc(@Valid @RequestBody ScoringDataDto request) {
        CreditDto response = calculatorServiceClient.calc(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
