package com.sosadwaden.deal.kafka;

import com.sosadwaden.deal.dto.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private static final Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    @Autowired
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public void sendMessage(String topic, EmailMessage message) {
        logger.info("Отправка сообщения в {}: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}