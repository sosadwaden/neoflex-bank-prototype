package com.sosadwaden.deal.service.impl;

import com.sosadwaden.deal.dto.statement.StatementDto;
import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.exception.StatementNotFoundException;
import com.sosadwaden.deal.mapperMapStruct.statement.StatementMapper;
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
    public StatementDto getStatement(UUID statementId) {

        Optional<Statement> optionalStatement = statementRepository.findById(statementId);

        if (optionalStatement.isEmpty()) {
            throw new StatementNotFoundException("Statement с таким UUID не найден");
        }

        Statement statement = optionalStatement.get();
        logger.debug("Запрос к БД прошёл успешно. Statement: {}", statement);

        return StatementMapper.INSTANCE.toDto(statement);
    }

    @Override
    public List<StatementDto> getStatements() {
        List<Statement> statements = statementRepository.findAll();
        logger.debug("Запрос к БД прошёл успешно. List: {}", statements);

        return StatementMapper.INSTANCE.toDtoList(statements);
    }
}
