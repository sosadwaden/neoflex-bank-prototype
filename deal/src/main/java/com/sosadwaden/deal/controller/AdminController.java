package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.statement.StatementDto;
import com.sosadwaden.deal.service.AdminDealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Admin controller", description = "Контроллер для путей /deal/admin")
@RequiredArgsConstructor
@RequestMapping("/deal/admin")
@RestController
public class AdminController {

    private final AdminDealService adminDealService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Operation(summary = "Получить заявку по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка получена успешно"),
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @GetMapping("/statement/{statementId}")
    public ResponseEntity<StatementDto> getStatement(@PathVariable UUID statementId) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /deal/admin/statement/{statementId} с id: {}", statementId);

        StatementDto response = adminDealService.getStatement(statementId);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /deal/admin/statement/{statementId}: {}, (время выполнения: {} ms)", response, duration);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Получить все заявки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявки успешно получены"),
    })
    @GetMapping("/statement")
    public ResponseEntity<List<StatementDto>> getStatements() {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос на /deal/admin/statement");

        List<StatementDto> response = adminDealService.getStatements();

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от /deal/admin/statement, количество заявок: {}, (время выполнения: {} ms)", response.size(), duration);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
