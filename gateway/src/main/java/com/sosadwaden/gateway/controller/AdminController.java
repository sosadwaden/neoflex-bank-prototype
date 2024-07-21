package com.sosadwaden.gateway.controller;

import com.sosadwaden.gateway.client.AdminDealServiceClient;
import com.sosadwaden.gateway.dto.statement.StatementDto;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Admin API", description = "Admin API для работы с заявками")
@Validated
@RequestMapping("/deal/admin")
@RestController
public class AdminController {

    private final AdminDealServiceClient adminDealServiceClient;

    @Autowired
    public AdminController(AdminDealServiceClient adminDealServiceClient) {
        this.adminDealServiceClient = adminDealServiceClient;
    }

    @GetMapping("/statement/{statementId}")
    public ResponseEntity<StatementDto> getStatement(@PathVariable UUID statementId) {
        StatementDto response = adminDealServiceClient.getStatement(statementId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/statement")
    public ResponseEntity<List<StatementDto>> getStatements() {
        List<StatementDto> response = adminDealServiceClient.getStatements();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
