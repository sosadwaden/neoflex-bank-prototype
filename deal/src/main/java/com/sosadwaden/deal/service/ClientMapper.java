package com.sosadwaden.deal.service;

import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.jsonb_entity.Employment;
import com.sosadwaden.deal.entity.jsonb_entity.Passport;

import java.util.UUID;

public class ClientMapper {

    public Client mapToClient(LoanStatementRequestDto request) {
        Passport passport = Passport.builder()
                .passportUUID(UUID.randomUUID())
                .series(request.getPassportSeries())
                .number(request.getPassportNumber())
                .build();

        Employment employment = Employment.builder()
                .build();

        return Client.builder()
                .clientId(null)
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
}
