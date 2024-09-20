package com.sosadwaden.dossier.kafka;

import com.sosadwaden.dossier.enums.Topic;
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
        return new NewTopic(Topic.FINISH_REGISTRATION.name(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicCreateDocuments() {
        return new NewTopic(Topic.CREATE_DOCUMENTS.name(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicSendDocuments() {
        return new NewTopic(Topic.SEND_DOCUMENTS.name(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicSendSes() {
        return new NewTopic(Topic.SEND_SES.name(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicCreditIssued() {
        return new NewTopic(Topic.CREDIT_ISSUED.name(), 1, (short) 1);
    }

    @Bean
    public NewTopic topicStatementDenied() {
        return new NewTopic(Topic.STATEMENT_DENIED.name(), 1, (short) 1);
    }
}