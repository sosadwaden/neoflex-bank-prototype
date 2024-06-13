package com.sosadwaden.deal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DealConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
