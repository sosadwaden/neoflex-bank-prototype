package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.AdminDealServiceClient;
import com.sosadwaden.gateway.dto.statement.StatementDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name = "Gateway Admin API", description = "Gateway Controller для перенаправления запросов на /deal/admin")
@Validated
@RequestMapping("/deal/admin")
@RestController
public class AdminGatewayController {

    private final AdminDealServiceClient adminDealServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(AdminGatewayController.class);

    @Autowired
    public AdminGatewayController(AdminDealServiceClient adminDealServiceClient) {
        this.adminDealServiceClient = adminDealServiceClient;
    }

    @Operation(summary = "Получить заявку по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка получена успешно"),
            @ApiResponse(responseCode = "404", description = "Statement не найден")
    })
    @GetMapping("/statement/{statementId}")
    public ResponseEntity<StatementDto> getStatement(@PathVariable UUID statementId) {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /deal/admin/statement/{statementId} с id: {}", statementId);

        StatementDto response = adminDealServiceClient.getStatement(statementId);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от Gateway по адресу /deal/admin/statement: {}, (время выполнения: {} ms)", response, duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Получить все заявки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявки успешно получены"),
    })
    @GetMapping("/statement")
    public ResponseEntity<List<StatementDto>> getStatements() {
        long startTime = System.currentTimeMillis();
        logger.info("Запрос в Gateway по адресу /deal/admin/statement");

        List<StatementDto> response = adminDealServiceClient.getStatements();

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Ответ от Gateway по адресу /deal/admin/statement, количество заявок: {}, (время выполнения: {} ms)", response.size(), duration);

        if (logger.isDebugEnabled()) {
            logger.debug("Детали заявок: {}", response.stream().map(StatementDto::getStatementId).collect(Collectors.toList()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
