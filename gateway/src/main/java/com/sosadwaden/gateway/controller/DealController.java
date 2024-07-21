package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.DealServiceClient;
import com.sosadwaden.gateway.dto.FinishRegistrationRequestDto;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Deal controller", description = "Контроллер для работы с заявками")
@Validated
@RequestMapping("/deal")
@RestController
public class DealController {

    private final DealServiceClient dealServiceClient;

    @Autowired
    public DealController(DealServiceClient dealServiceClient) {
        this.dealServiceClient = dealServiceClient;
    }

    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> statement(@RequestBody LoanStatementRequestDto request) {
        List<LoanOfferDto> response = dealServiceClient.statement(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/offer/select")
    public ResponseEntity<Void> select(@RequestBody LoanOfferDto request) {
        dealServiceClient.select(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculate(@PathVariable String statementId,
                                          @RequestBody FinishRegistrationRequestDto request) {
        dealServiceClient.calculate(request, statementId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
