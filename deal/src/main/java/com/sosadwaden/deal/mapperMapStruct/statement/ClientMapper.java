package com.sosadwaden.deal.mapperMapStruct.statement;

import com.sosadwaden.deal.dto.statement.ClientDto;
import com.sosadwaden.deal.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientDto toDto(Client client);
}
