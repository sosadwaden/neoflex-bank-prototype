package com.sosadwaden.dossier.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicFinishRegistration() {
        return new NewTopic("finish-registration", 1, (short) 1);
    }

    @Bean
    public NewTopic topicCreateDocuments() {
        return new NewTopic("create-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic topicSendDocuments() {
        return new NewTopic("send-documents", 1, (short) 1);
    }

    @Bean
    public NewTopic topicSendSes() {
        return new NewTopic("send-ses", 1, (short) 1);
    }

    @Bean
    public NewTopic topicCreditIssued() {
        return new NewTopic("credit-issued", 1, (short) 1);
    }

    @Bean
    public NewTopic topicStatementDenied() {
        return new NewTopic("statement-denied", 1, (short) 1);
    }
}
