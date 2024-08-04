package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.CreditDto;
import com.sosadwaden.deal.entity.Credit;
import org.mapstruct.Mapper;

@Mapper
public interface CreditMapper {
    CreditDto toDto(Credit credit);
}
