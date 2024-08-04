package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Deal controller", description = "Контроллер для путей /deal")
@RequiredArgsConstructor
@RequestMapping("/deal")
@RestController
public class DealController {

    private final DealService dealService;
    private static final Logger logger = LoggerFactory.getLogger(DealController.class);

    @Operation(
            summary = "Расчёт возможных условий кредита",
            description = "Создать Client и Statement сущности; присвоить id созданной заявки Statement"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Расчёт прошёл успешно"),
    })
    @PostMapping("${application.endpoint.statement}")
    public ResponseEntity<List<LoanOfferDto>> statement(@RequestBody LoanStatementRequestDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /deal/statement: {}", request);

        List<LoanOfferDto> response = dealService.statement(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /deal/statement: {}, (время выполнения: {} ms)", response, duration);

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
    @PostMapping("${application.endpoint.offerSelect}")
    public ResponseEntity<Void> select(@RequestBody LoanOfferDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /deal/offer/select: {}", request);

        dealService.offerSelect(request);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Запрос по адресу /deal/offer/select выполнен успешно; (время выполнения: {} ms)", duration);

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
    @PostMapping("${application.endpoint.calculate}/{statementId}")
    public ResponseEntity<Void> calculate(@PathVariable String statementId,
                          @RequestBody FinishRegistrationRequestDto request) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /deal/calculate/{statementId}: {}", request);

        dealService.calculateByStatementId(request, statementId);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Запрос по адресу /deal/calculate/{statementId} выполнен успешно; (время выполнения: {} ms)", duration);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
