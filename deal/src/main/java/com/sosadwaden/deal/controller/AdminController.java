package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.entity.Statement;
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

@RequiredArgsConstructor
@RequestMapping("/deal/admin")
@Tag(name = "Admin API", description = "Admin API для работы с заявками")
@RestController
public class AdminController {

    private final AdminDealService adminDealService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Operation(summary = "Получить заявку по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка получена успешно"),
    })
    @GetMapping("/statement/{statementId}")
    public ResponseEntity<Statement> getStatement(@PathVariable UUID statementId) {
        logger.info("Запрос на /deal/admin/statement/{statementId} с id: {}", statementId);
        Statement response = adminDealService.getStatement(statementId);

        logger.info("Ответ от /deal/admin/statement/{statementId}: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Получить все заявки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявки успешно получены"),
    })
    @GetMapping
    public ResponseEntity<List<Statement>> getStatements() {
        logger.info("Запрос на /deal/admin/statement");
        List<Statement> response = adminDealService.getStatements();

        logger.info("Ответ от /deal/admin/statement: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
