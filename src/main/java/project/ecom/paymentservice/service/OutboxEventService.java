package project.ecom.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import project.ecom.paymentservice.entity.OutboxEvent;
import project.ecom.paymentservice.event.Event;
import project.ecom.paymentservice.repository.OutboxEventRepository;

import java.util.List;

@Component
public class OutboxEventService {

    private ObjectMapper objectMapper;
    private OutboxEventRepository outboxEventRepository;

    public OutboxEventService(ObjectMapper objectMapper, OutboxEventRepository outboxEventRepository) {
        this.objectMapper = objectMapper;
        this.outboxEventRepository = outboxEventRepository;
    }

    public void saveOutboxEvent(Event event) {
        OutboxEvent outboxEvent = new OutboxEvent(event.eventId(), "", false, event.eventType(), event.aggregateType(), event.aggregateId());
        try {
            String payload = objectMapper.writeValueAsString(event);
            outboxEvent.setMessage(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize payment succeeded event", e);
        }
        outboxEventRepository.save(outboxEvent);
    }

    public void save(List<OutboxEvent> outboxEvents) {
        outboxEventRepository.saveAll(outboxEvents);
    }

    public List<OutboxEvent> findUnpublishedEvents() {
        Pageable pageable = PageRequest.ofSize(100);
        return outboxEventRepository.findByIsPublishedFalseOrderByCreatedAtAsc(pageable);
    }
}
