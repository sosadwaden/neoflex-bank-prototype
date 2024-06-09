package com.sosadwaden.calculator.controller;

import com.sosadwaden.calculator.dto.CreditDto;
import com.sosadwaden.calculator.dto.LoanOfferDto;
import com.sosadwaden.calculator.dto.LoanStatementRequestDto;
import com.sosadwaden.calculator.dto.ScoringDataDto;
import com.sosadwaden.calculator.service.CalculatorService;
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

@Tag(name = "Calculator controller", description = "Контроллер для путей /calculator")
@Validated
//@RequiredArgsConstructor
@RequestMapping("/calculator")
@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     *  Расчёт возможных условий кредита
     * @param request
     * @return List<LoanOfferDto>
     */
    @Operation(
            summary = "Создать кредитные предложения",
            description = "Создать список кредитных предложений от худшего к лучшему"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кредитные предложения успешно созданы"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping("${application.endpoint.offers}")
    public ResponseEntity<List<LoanOfferDto>> offers(@Valid @RequestBody LoanStatementRequestDto request) {
            logger.info("Полученный запрос: {}", request);
            List<LoanOfferDto> response = calculatorService.generateOffers(request);

            logger.info("Сгенерированный ответ: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Валидация присланных данных +
     * скоринг данных +
     * полный расчет параметров кредита.
     * @param request
     * @return
     */
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
    @PostMapping("${application.endpoint.calc}")
    public ResponseEntity<CreditDto> calc(@Valid @RequestBody ScoringDataDto request) {
        logger.info("Полученный запрос: {}", request);
        CreditDto response = calculatorService.generateCreditDto(request);

        logger.info("Сгенерированный ответ: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
