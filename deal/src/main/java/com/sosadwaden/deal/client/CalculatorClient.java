package com.sosadwaden.deal.client;

import com.sosadwaden.deal.dto.CreditDto;
import com.sosadwaden.deal.dto.LoanOfferDto;
import com.sosadwaden.deal.dto.LoanStatementRequestDto;
import com.sosadwaden.deal.dto.ScoringDataDto;
import com.sosadwaden.deal.service.impl.DealServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CalculatorClient {

    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);
    private final RestTemplate restTemplate;

    public List<LoanOfferDto> sendPostRequestToCalculatorOffers(LoanStatementRequestDto request) {
        logger.debug("Отправка POST запроса /calculator/offers");
        logger.info("LoanStatementRequestDto request на /calculator/offers: {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:4455/calculator/offers";

        ResponseEntity<List<LoanOfferDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<LoanOfferDto>>() {
                }
        );

        List<LoanOfferDto> responseBody = response.getBody();

        if (responseBody == null) {
            logger.warn("Получен пустой ответ от /calculator/offers");
            responseBody = new ArrayList<>();
        }

        return responseBody;
    }

    public CreditDto sendPostRequestToCalculatorCals(ScoringDataDto request) {
        logger.debug("Отправка POST запроса /calculator/calc");
        logger.info("ScoringDataDto request на /calculator/calc: {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ScoringDataDto> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:4455/calculator/calc";

        ResponseEntity<CreditDto> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CreditDto.class
        );

        CreditDto responseBody = response.getBody();

        if (responseBody == null) {
            logger.warn("Получен пустой ответ от /calculator/calc");
            responseBody = CreditDto.builder().build();
        }

        return responseBody;
    }
}
