package project.ecom.paymentservice.event;

import project.ecom.paymentservice.entity.AggregateType;
import project.ecom.paymentservice.entity.EventType;

import java.util.UUID;

public record PaymentEvent(
        UUID eventId,
        Long paymentId,
        Long orderId,
        Long amount,
        EventType eventType
) implements Event{
    @Override
    public UUID eventId() {
        return eventId;
    }

    @Override
    public AggregateType aggregateType() {
        return AggregateType.PAYMENT;
    }

    @Override
    public Long aggregateId() {
        return paymentId;
    }

    public EventType eventType(){
        return eventType;
    }

}
