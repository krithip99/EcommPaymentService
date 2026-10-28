package project.ecom.paymentservice.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "source_provider_event_id",
                        columnNames = {"source_provider", "source_event_id"}
                )
        }
)
public class OutboxEvent extends BaseClass {
    @Column(nullable = false, name = "source_provider")
    @Enumerated(EnumType.STRING)
    private SourceProvider sourceProvider;

    @Column(nullable = false, name = "source_event_id")
    private Long sourceEventId;

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

    public OutboxEvent(UUID eventId, String message, boolean isPublished, EventType eventType, AggregateType aggregateType, Long aggregateId,
                       SourceProvider sourceProvider, Long sourceEventId) {
        this.eventId = eventId;
        this.message = message;
        this.isPublished = isPublished;
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.sourceProvider = sourceProvider;
        this.sourceEventId = sourceEventId;
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
