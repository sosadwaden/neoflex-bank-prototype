package com.sosadwaden.statement.controller;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
import com.sosadwaden.statement.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RequestMapping("/statement")
@RestController
public class StatementController {

    private final StatementService statementService;
    private static final Logger logger = LoggerFactory.getLogger(StatementController.class);

    @Operation(
            summary = "Запрос на расчёт условий кредита",
            description = "Прескоринг и запрос на расчёт возможных условий кредита"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос на расчёт выполнены успешно"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping
    public ResponseEntity<List<LoanOfferDto>> calculationOfPossibleLoanTerms(@Valid @RequestBody LoanStatementRequestDto request) {
        logger.info("Запрос на /statement: {}", request);
        List<LoanOfferDto> response = statementService.calculationOfPossibleLoanTerms(request);

        logger.info("Ответ от /statement: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Выбор одного из предложений"
    )
    @PostMapping("${application.endpoint.offer}")
    public ResponseEntity<Void> offer(@RequestBody LoanOfferDto request) {
        statementService.offer(request);
        logger.info("Запрос на /statement/offer: {}", request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
