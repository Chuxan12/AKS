package com.example.academy.spring.messaging;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChangeEventAlertListener {

    private static final Logger log = LoggerFactory.getLogger(ChangeEventAlertListener.class);
    private final JavaMailSender mailSender;

    public ChangeEventAlertListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @JmsListener(destination = ChangeEventPublisher.DESTINATION, containerFactory = "topicListenerFactory")
    public void onMessage(ChangeEvent event) {
        if (event == null || !StringUtils.hasText(event.getEntity()) || !StringUtils.hasText(event.getOperation())) {
            log.warn("AlertListener: пустое/некорректное событие: {}", event);
            return;
        }
        if (!shouldNotify(event)) {
            return;
        }
        sendMail(event);
    }

    private boolean shouldNotify(ChangeEvent event) {
        if (!"Student".equalsIgnoreCase(event.getEntity())) {
            return false;
        }
        Map<String, Object> payload = event.getPayload();
        Object year = payload != null ? payload.get("studyYear") : null;
        if (year instanceof Number) {
            return ((Number) year).intValue() >= 3;
        }
        return false;
    }

    private void sendMail(ChangeEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@academy.local");
            message.setTo("alerts@example.com");
            message.setSubject("Изменение сущности " + event.getEntity());
            message.setText("Операция: " + event.getOperation() + "\n" +
                    "ID: " + event.getEntityId() + "\n" +
                    "Данные: " + event.getPayload());
            mailSender.send(message);
            log.info("AlertListener отправил письмо: {}", event);
        } catch (Exception e) {
            log.error("Не удалось отправить письмо об изменении {}", event, e);
        }
    }
}
