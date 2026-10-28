package project.ecom.paymentservice.publisher;

import project.ecom.paymentservice.entity.OutboxEvent;

import java.util.List;

public interface EventPublisher {
    void publishEvent(List<OutboxEvent> events);
}
