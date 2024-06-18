package com.sosadwaden.statement.client;

import com.sosadwaden.statement.dto.LoanOfferDto;
import com.sosadwaden.statement.dto.LoanStatementRequestDto;
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
public class DealClient {

    private static final Logger logger = LoggerFactory.getLogger(DealClient.class);
    private final RestTemplate restTemplate;

    public List<LoanOfferDto> sendPostRequestToDealStatement(LoanStatementRequestDto request) {
        logger.debug("Отправка POST запроса /deal/statement");
        logger.info("LoanStatementRequestDto request на /deal/statement: {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:4456/deal/statement";

        ResponseEntity<List<LoanOfferDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<LoanOfferDto>>() {
                }
        );

        List<LoanOfferDto> responseBody = response.getBody();

        if (responseBody == null) {
            logger.warn("Получен пустой ответ от /deal/statement");
            responseBody = new ArrayList<>();
        }

        return responseBody;
    }

    public void sendPostRequestToDealOfferSelect(LoanOfferDto request) {
        logger.debug("Отправка POST запроса /deal/offer/select");
        logger.info("LoanStatementRequestDto request на /deal/offer/select: {}", request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);
        String url = "http://localhost:4456/deal/offer/select";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<LoanOfferDto>>() {
                }
        );
    }

}
