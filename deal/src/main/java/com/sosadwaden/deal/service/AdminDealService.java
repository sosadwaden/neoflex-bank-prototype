package com.sosadwaden.deal.service;

import com.sosadwaden.deal.dto.statement.StatementDto;

import java.util.List;
import java.util.UUID;

public interface AdminDealService {

    StatementDto getStatement(UUID statementId);

    List<StatementDto> getStatements();
}
