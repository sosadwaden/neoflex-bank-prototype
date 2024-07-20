package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.PassportDto;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;
import org.mapstruct.Mapper;

@Mapper
public interface PassportMapper {
    PassportDto toDto(Passport passport);
}
