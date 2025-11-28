package com.example.academy.spring.messaging;

import java.util.HashMap;
import java.util.Map;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventPublisher {

    public static final String DESTINATION = "academy.change.events";
    private final JmsTemplate jmsTemplate;

    public ChangeEventPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publish(String entity, Long id, String operation, Map<String, Object> payload) {
        Map<String, Object> safePayload = payload != null ? payload : new HashMap<>();
        ChangeEvent event = new ChangeEvent(entity, id, operation, safePayload);
        jmsTemplate.convertAndSend(DESTINATION, event);
    }
}
