package com.sosadwaden.statement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class StatementConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
