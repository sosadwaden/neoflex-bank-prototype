package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.StatementServiceClient;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.statement.LoanOfferDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Tag(name = "Gateway Statement API", description = "Gateway Controller для перенаправления запросов на /statement")
@Validated
@RequestMapping("/statement")
@RestController
public class StatementGatewayController {

    private final StatementServiceClient statementServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(StatementGatewayController.class);

    @Autowired
    public StatementGatewayController(StatementServiceClient statementServiceClient) {
        this.statementServiceClient = statementServiceClient;
    }

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
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /statement: {}", request);
        List<LoanOfferDto> response = statementServiceClient.calculationOfPossibleLoanTerms(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от Gateway по адресу /statement: {}, (время выполнения: {} ms)", response, duration);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Выбор одного из предложений")
    @PostMapping("/offer")
    public ResponseEntity<Void> offer(@RequestBody LoanOfferDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /statement/offer: {}", request);

        statementServiceClient.offer(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Gateway, запрос по адресу /deal/admin/statement выполнен; (время выполнения: {} ms)", duration);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
