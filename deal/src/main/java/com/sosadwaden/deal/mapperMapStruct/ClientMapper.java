package com.sosadwaden.deal.mapperMapStruct;

import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "passportId", source = "request")
    @Mapping(target = "employmentId", expression = "java(createEmployment())")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "birthdate", source = "birthdate")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "clientId",ignore = true)
    @Mapping(target = "gender",ignore = true)
    @Mapping(target = "maritalStatus",ignore = true)
    @Mapping(target = "dependentAmount",ignore = true)
    @Mapping(target = "accountNumber",ignore = true)
    @Mapping(target = "statements",ignore = true)
    Client loanStatementRequestDtoToClient(LoanStatementRequestDto request);

    default Passport mapToPassport(LoanStatementRequestDto request) {
        return Passport.builder()
                .series(request.getPassportSeries())
                .number(request.getPassportNumber())
                .build();
    }

    default Employment createEmployment() {
        return Employment.builder().build();
    }
}
