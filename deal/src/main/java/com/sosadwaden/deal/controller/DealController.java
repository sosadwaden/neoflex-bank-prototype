package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiResponse(responseCode = "200", description = "Расчёт прошёл успешно"),
    })
    @PostMapping("${application.endpoint.statement}")
    public ResponseEntity<List<LoanOfferDto>> statement(@RequestBody LoanStatementRequestDto request) {
        logger.info("Полученный запрос: {}", request);
        List<LoanOfferDto> response = dealService.statement(request);

        logger.info("Сгенерированный ответ: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление заявки Statement",
            description = "В заявке обновляется статус, история статусов, " +
                          "принятое предложение LoanOfferDto устанавливается в поле appliedOffer."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @PostMapping("${application.endpoint.offerSelect}")
    public void select(@RequestBody LoanOfferDto request) {
        logger.info("Полученный запрос: {}", request);
        dealService.offerSelect(request);
    }

    @Operation(
            summary = "Завершение регистрации + полный подсчёт кредита",
            description = "Создаётся сущность Credit и сохраняется в базу данных"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @PostMapping("/calculate/{statementId}")
    public void calculate(@PathVariable String statementId,
                          @RequestBody FinishRegistrationRequestDto request) {
        logger.info("Полученный запрос: {}", request);
        dealService.calculateByStatementId(request, statementId);
    }
}
