package com.sosadwaden.deal.controller;

import com.sosadwaden.deal.dto.EmailMessage;
import com.sosadwaden.deal.dto.Theme;
import com.sosadwaden.deal.kafka.EmailProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deal/document")
@Tag(name = "Document API", description = "API для работы с документами")
public class DocumentController {

    @Autowired
    private EmailProducer emailProducer;

    @Operation(summary = "Запрос на отправку документов")
    @PostMapping("/{statementId}/send")
    public void sendDocument(
            @Parameter(description = "ID заявления", required = true) @PathVariable Long statementId,
            @Parameter(description = "Сообщение электронной почты", required = true) @RequestBody EmailMessage emailMessage) {
        emailMessage.setStatementId(statementId);
        emailMessage.setTheme(Theme.SEND_DOCUMENTS);
        emailProducer.sendMessage("send-documents", emailMessage);
    }

    @Operation(summary = "Запрос на подписание документов")
    @PostMapping("/{statementId}/sign")
    public void signDocument(
            @Parameter(description = "ID заявления", required = true) @PathVariable Long statementId,
            @Parameter(description = "Сообщение электронной почты", required = true) @RequestBody EmailMessage emailMessage) {
        emailMessage.setStatementId(statementId);
        emailMessage.setTheme(Theme.SEND_SES);
        emailProducer.sendMessage("send-ses", emailMessage);
    }

    @Operation(summary = "Подписание документов")
    @PostMapping("/{statementId}/code")
    public void codeDocument(
            @Parameter(description = "ID заявления", required = true) @PathVariable Long statementId,
            @Parameter(description = "Сообщение электронной почты", required = true) @RequestBody EmailMessage emailMessage) {
        emailMessage.setStatementId(statementId);
        emailMessage.setTheme(Theme.CREDIT_ISSUED);
        emailProducer.sendMessage("credit-issued", emailMessage);
    }

}
