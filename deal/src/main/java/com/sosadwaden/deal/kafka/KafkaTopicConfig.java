package com.sosadwaden.deal.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return new NewTopic("finish-registration", 1, (short) 1);
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return new NewTopic("create-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return new NewTopic("send-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic sendSesTopic() {
        return new NewTopic("send-ses", 1, (short) 1);
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return new NewTopic("credit-issued", 1, (short) 1);
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return new NewTopic("statement-denied", 1, (short) 1);
    }
}
