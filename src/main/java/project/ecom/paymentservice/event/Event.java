package project.ecom.paymentservice.event;

import project.ecom.paymentservice.entity.EventType;

import java.util.UUID;

public interface Event {
    UUID eventId();
    Aggregate aggregate();
    Long aggregateId();
    EventType eventType();
}
