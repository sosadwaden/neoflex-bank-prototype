package com.sosadwaden.deal.service.impl;

import com.sosadwaden.deal.dto.FinishRegistrationRequestDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.entity.Client;
import com.sosadwaden.deal.entity.Statement;
import com.sosadwaden.deal.entity.enums.ApplicationStatus;
import com.sosadwaden.deal.repository.ClientRepository;
import com.sosadwaden.deal.repository.StatementRepository;
import com.sosadwaden.deal.service.ClientMapper;
import com.sosadwaden.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;
    private final ClientMapper clientMapper;
    private final RestTemplate restTemplate;

    @Override
    public List<LoanOfferDto> statement(LoanStatementRequestDto request) {
        Client client = clientMapper.LoanStatementRequestDtoToClient(request);
        clientRepository.save(client);

        Statement statement = Statement.builder()
                .client(client)
                .build();

        statementRepository.save(statement);

        List<LoanOfferDto> responseBody = sendPostRequestToCalculatorOffers(request);

        UUID statementId = statement.getStatementId();

        responseBody.get(0).setStatementId(statementId);
        responseBody.get(1).setStatementId(statementId);
        responseBody.get(2).setStatementId(statementId);
        responseBody.get(3).setStatementId(statementId);

        return responseBody;
    }

    @Override
    public void offerSelect(LoanOfferDto request) {
        Statement statement = statementRepository.getReferenceById(request.getStatementId());

        statement.setStatus(ApplicationStatus.APPROVED);
    }

    @Override
    public void calculateByStatementId(FinishRegistrationRequestDto request,
                                       String statementId) {

    }

    private List<LoanOfferDto> sendPostRequestToCalculatorOffers(LoanStatementRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:4455/calculator/offers";

        ResponseEntity<List<LoanOfferDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<LoanOfferDto>>() {}
        );

        return response.getBody();
    }
}
