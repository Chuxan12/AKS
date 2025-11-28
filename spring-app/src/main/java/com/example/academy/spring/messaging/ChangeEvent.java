package com.example.academy.spring.messaging;

import java.io.Serializable;
import java.util.Map;

public class ChangeEvent implements Serializable {
    private String entity;
    private Long entityId;
    private String operation;
    private Map<String, Object> payload;

    public ChangeEvent() {
    }

    public ChangeEvent(String entity, Long entityId, String operation, Map<String, Object> payload) {
        this.entity = entity;
        this.entityId = entityId;
        this.operation = operation;
        this.payload = payload;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
