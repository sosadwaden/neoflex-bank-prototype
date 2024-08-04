package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.StatusHistoryDto;
import com.sosadwaden.deal.entity.jsonb_entity.StatusHistory;
import org.mapstruct.Mapper;

@Mapper
public interface StatusHistoryMapper {
    StatusHistoryDto toDto(StatusHistory statusHistory);
}
