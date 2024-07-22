package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.DealServiceClient;
import com.sosadwaden.gateway.dto.FinishRegistrationRequestDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Gateway Deal API", description = "Gateway Controller для перенаправления запросов на /deal")
@Validated
@RequestMapping("/deal")
@RestController
public class DealGatewayController {

    private final DealServiceClient dealServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(DealGatewayController.class);

    @Autowired
    public DealGatewayController(DealServiceClient dealServiceClient) {
        this.dealServiceClient = dealServiceClient;
    }

    @Operation(
            summary = "Расчёт возможных условий кредита",
            description = "Создать Client и Statement сущности; присвоить id созданной заявки Statement"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Расчёт прошёл успешно"),
    })
    @PostMapping("/statement")
    public ResponseEntity<List<LoanOfferDto>> statement(@RequestBody LoanStatementRequestDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /deal/statement: {}", request);

        List<LoanOfferDto> response = dealServiceClient.statement(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от Gateway по адресу /deal/statement: {}, (время выполнения: {} ms)", response, duration);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Обновление заявки Statement",
            description = "В заявке обновляется статус, история статусов, " +
                          "принятое предложение LoanOfferDto устанавливается в поле appliedOffer."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Заявка успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @PostMapping("/offer/select")
    public ResponseEntity<Void> select(@RequestBody LoanOfferDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /deal/offer/select: {}", request);

        dealServiceClient.select(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Gateway, запрос по адресу /deal/offer/select выполнен; (время выполнения: {} ms)", duration);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Завершение регистрации + полный подсчёт кредита",
            description = "Создаётся сущность Credit и сохраняется в базу данных"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредит успешно рассчитан и заявка обновлена"),
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @PostMapping("/calculate/{statementId}")
    public ResponseEntity<Void> calculate(@PathVariable String statementId,
                                          @RequestBody FinishRegistrationRequestDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /deal/calculate: {}", request);

        dealServiceClient.calculate(request, statementId);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Gateway, запрос по адресу /calculator/offers выполнен; (время выполнения: {} ms)", duration);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
