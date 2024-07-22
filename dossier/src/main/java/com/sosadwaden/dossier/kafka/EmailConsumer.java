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
        sendEmail(message);
    }

    @KafkaListener(topics = "create-documents", groupId = "dossier-group")
    public void processCreateDocuments(EmailMessage message) {
        sendEmail(message);
    }

    @KafkaListener(topics = "send-documents", groupId = "dossier-group")
    public void processSendDocuments(EmailMessage message) {
        sendEmail(message);
    }

    @KafkaListener(topics = "send-ses", groupId = "dossier-group")
    public void processSendSes(EmailMessage message) {
        sendEmail(message);
    }

    @KafkaListener(topics = "credit-issued", groupId = "dossier-group")
    public void processCreditIssued(EmailMessage message) {
        sendEmail(message);
    }

    @KafkaListener(topics = "statement-denied", groupId = "dossier-group")
    public void processStatementDenied(EmailMessage message) {
        sendEmail(message);
    }

    private void sendEmail(EmailMessage message) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(message.getAddress());
            mailMessage.setSubject(String.valueOf(message.getTopic()));
            mailMessage.setText("Ваше заявление " + message.getStatementId() + " обработано.");
            mailSender.send(mailMessage);
            logger.info("Электронное письмо отправлено по адресу {}", message.getAddress());
        } catch (Exception e) {
            logger.error("Не удалось отправить электронное письмо", e);
        }
    }
}
