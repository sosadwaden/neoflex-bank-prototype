package com.sosadwaden.deal.service;

import com.sosadwaden.deal.entity.Statement;

import java.util.List;
import java.util.UUID;

public interface AdminDealService {

    Statement getStatement(UUID statementId);

    List<Statement> getStatements();
}
