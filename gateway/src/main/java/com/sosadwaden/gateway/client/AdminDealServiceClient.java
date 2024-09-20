package com.sosadwaden.gateway.client;

import com.sosadwaden.gateway.dto.statement.StatementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "admin-service", url = "http://deal:4456")
public interface AdminDealServiceClient {

    @GetMapping("/deal/admin/statement/{statementId}")
    StatementDto getStatement(@PathVariable UUID statementId);

    @GetMapping("/deal/admin/statement")
    List<StatementDto> getStatements();
}
