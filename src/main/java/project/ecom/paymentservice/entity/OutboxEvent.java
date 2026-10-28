package project.ecom.paymentservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.UUID;

@Entity
public class OutboxEvent extends BaseClass {

    @Column(nullable = false)
    private UUID eventId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AggregateType aggregateType;

    @Column(nullable = false)
    private Long aggregateId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isPublished;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    public OutboxEvent(UUID eventId, String message, boolean isPublished, EventType eventType, AggregateType aggregateType, Long aggregateId) {
        this.eventId = eventId;
        this.message = message;
        this.isPublished = isPublished;
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
