package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.CalculatorServiceClient;
import com.sosadwaden.gateway.dto.CreditDto;
import com.sosadwaden.gateway.dto.LoanStatementRequestDto;
import com.sosadwaden.gateway.dto.ScoringDataDto;
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

@Tag(name = "Gateway Calculator API", description = "Gateway Controller для перенаправления запросов на /calculator")
@Validated
@RequestMapping("/calculator")
@RestController
public class CalculatorGatewayController {

    private final CalculatorServiceClient calculatorServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorGatewayController.class);

    @Autowired
    public CalculatorGatewayController(CalculatorServiceClient calculatorServiceClient) {
        this.calculatorServiceClient = calculatorServiceClient;
    }

    @Operation(
            summary = "Создать кредитные предложения",
            description = "Создать список кредитных предложений от худшего к лучшему"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредитные предложения успешно созданы"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> offers(@Valid @RequestBody LoanStatementRequestDto request) {
        logger.info("Запрос в Gateway по адресу /calculator/offers: {}", request);
        List<LoanOfferDto> response = calculatorServiceClient.offers(request);

        logger.info("Ответ от Gateway по адресу /calculator/offers: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Получить параметры кредита",
            description = "Контроллер возвращает итоговую ставку (rate), полную стоимость кредит (psk), " +
                          "размер ежемесячного платежа (monthlyPayment) и график ежемесячных " +
                          "платежей List<PaymentScheduleElementDto>"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Параметры кредита успешно созданы"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации для Enum'ов"),
            @ApiResponse(responseCode = "422", description = "Скоринг не прошёл")
    })
    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calc(@Valid @RequestBody ScoringDataDto request) {
        logger.info("Запрос в Gateway по адресу /calculator/calc: {}", request);
        CreditDto response = calculatorServiceClient.calc(request);

        logger.info("Ответ от Gateway по адресу /calculator/calc: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
