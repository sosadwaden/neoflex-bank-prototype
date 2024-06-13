package com.sosadwaden.deal.service;

import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientMapper {

    public Client LoanStatementRequestDtoToClient(LoanStatementRequestDto request) {
        Passport passport = Passport.builder()
                .series(request.getPassportSeries())
                .number(request.getPassportNumber())
                .build();

        Employment employment = Employment.builder()
                .build();

        return Client.builder()
                .clientId(UUID.randomUUID())
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .passportId(passport)
                .employmentId(employment)
                .build();
    }
}
