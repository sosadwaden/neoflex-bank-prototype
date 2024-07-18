package com.sosadwaden.deal.service.impl;

import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.repository.StatementRepository;
import com.sosadwaden.deal.service.AdminDealService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminDealServiceImpl implements AdminDealService {

    private final StatementRepository statementRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminDealServiceImpl.class);

    @Override
    public Statement getStatement(UUID statementId) {
        // TODO добавить проверку
        Statement statement = statementRepository.findById(statementId).get();
        logger.debug("Запрос к БД прошёл успешно. Statement: {}", statement);

        return statement;
    }

    @Override
    public List<Statement> getStatements() {
        List<Statement> statements = statementRepository.findAll();
        logger.debug("Запрос к БД прошёл успешно. List: {}", statements);

        return statements;
    }
}
