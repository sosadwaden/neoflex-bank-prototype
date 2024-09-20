package com.sosadwaden.dossier.kafka;

import com.sosadwaden.dossier.dto.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "finish-registration", groupId = "dossier-group")
    public void processFinishRegistration(EmailMessage message) {
        handleEmailMessage(message, "finish-registration");
    }

    @KafkaListener(topics = "create-documents", groupId = "dossier-group")
    public void processCreateDocuments(EmailMessage message) {
        handleEmailMessage(message, "create-documents");
    }

    @KafkaListener(topics = "send-documents", groupId = "dossier-group")
    public void processSendDocuments(EmailMessage message) {
        handleEmailMessage(message, "send-documents");
    }

    @KafkaListener(topics = "send-ses", groupId = "dossier-group")
    public void processSendSes(EmailMessage message) {
        handleEmailMessage(message, "send-ses");
    }

    @KafkaListener(topics = "credit-issued", groupId = "dossier-group")
    public void processCreditIssued(EmailMessage message) {
        handleEmailMessage(message, "credit-issued");
    }

    @KafkaListener(topics = "statement-denied", groupId = "dossier-group")
    public void processStatementDenied(EmailMessage message) {
        handleEmailMessage(message, "statement-denied");
    }

    private void handleEmailMessage(EmailMessage message, String topic) {
        logger.info("Получено сообщение из Kafka топика {}: {}", topic, message);
        try {
            sendEmail(message);
            logger.info("Электронное письмо успешно отправлено по адресу {} для топика {}", message.getAddress(), topic);
        } catch (Exception e) {
            logger.error("Не удалось отправить электронное письмо по адресу {} для топика {}", message.getAddress(), topic, e);
        }
    }

    private void sendEmail(EmailMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(message.getAddress());
        mailMessage.setSubject("Ваше заявление " + message.getStatementId() + " обработано.");
        mailMessage.setText("Ваше заявление " + message.getStatementId() + " по теме " + message.getTopic() + " было обработано.");
        mailSender.send(mailMessage);
    }
}
