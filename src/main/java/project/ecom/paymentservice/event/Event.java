package project.ecom.paymentservice.event;

import project.ecom.paymentservice.entity.AggregateType;
import project.ecom.paymentservice.entity.EventType;

import java.util.UUID;

public interface Event {
    UUID eventId();
    AggregateType aggregateType();
    Long aggregateId();
    EventType eventType();
}
