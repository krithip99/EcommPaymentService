package project.ecom.paymentservice.relay;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.ecom.paymentservice.entity.OutboxEvent;
import project.ecom.paymentservice.publisher.EventPublisher;
import project.ecom.paymentservice.service.OutboxEventService;

import java.util.List;

@Component
public class OutboxRelay {
    private OutboxEventService outboxEventService;
    private EventPublisher eventPublisher;
    public OutboxRelay(OutboxEventService outboxEventService, EventPublisher eventPublisher){
        this.outboxEventService = outboxEventService;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishEventsToKafka(){
        List<OutboxEvent> outboxEvents = outboxEventService.findUnpublishedEvents();
        eventPublisher.publishEvent(outboxEvents);
        outboxEventService.save(outboxEvents);
    }
}
