package com.example.academy.spring.messaging;

import com.example.academy.spring.model.ChangeLog;
import com.example.academy.spring.repository.ChangeLogRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ChangeEventLogListener {

    private static final Logger log = LoggerFactory.getLogger(ChangeEventLogListener.class);
    private final ChangeLogRepository changeLogRepository;

    public ChangeEventLogListener(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    @JmsListener(destination = ChangeEventPublisher.DESTINATION, containerFactory = "topicListenerFactory")
    public void onMessage(ChangeEvent event) {
        if (event == null || !StringUtils.hasText(event.getEntity()) || !StringUtils.hasText(event.getOperation())) {
            log.warn("Получено пустое/некорректное событие JMS: {}", event);
            return;
        }
        log.info("Листенер логов получил событие: {}", event);
        ChangeLog row = new ChangeLog();
        row.setCreatedAt(Instant.now());
        row.setEntityName(event.getEntity());
        row.setEntityId(event.getEntityId());
        row.setOperation(event.getOperation());
        row.setDetails(event.getPayload() != null ? event.getPayload().toString() : "");
        changeLogRepository.save(row);
    }
}
