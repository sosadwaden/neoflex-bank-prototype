package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.StatementDto;
import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.mapperMapStruct.PaymentScheduleElementMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        ClientMapper.class,
        CreditMapper.class,
        PassportMapper.class,
        EmploymentMapper.class,
        PaymentScheduleElementMapper.class,
        StatusHistoryMapper.class
})
public interface StatementMapper {

    StatementMapper INSTANCE = Mappers.getMapper(StatementMapper.class);

    @Mapping(source = "client", target = "client")
    @Mapping(source = "credit.creditId", target = "credit")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "creationDate", target = "creationDate")
    @Mapping(source = "appliedOffer", target = "appliedOffer")
    @Mapping(source = "signDate", target = "signDate")
    @Mapping(source = "sesCode", target = "sesCode")
    @Mapping(source = "statusHistory", target = "statusHistory")
    StatementDto toDto(Statement statement);

    List<StatementDto> toDtoList(List<Statement> statements);
}
