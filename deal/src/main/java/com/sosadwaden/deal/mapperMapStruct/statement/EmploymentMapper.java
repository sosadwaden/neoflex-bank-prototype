package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.EmploymentDto;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import org.mapstruct.Mapper;

@Mapper
public interface EmploymentMapper {
    EmploymentDto toDto(Employment employment);
}
