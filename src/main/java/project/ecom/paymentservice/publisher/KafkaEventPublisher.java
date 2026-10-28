package project.ecom.paymentservice.publisher;

import org.springframework.stereotype.Component;
import project.ecom.paymentservice.client.KafkaProducerClient;
import project.ecom.paymentservice.entity.OutboxEvent;

import java.util.List;

@Component
public class KafkaEventPublisher implements EventPublisher {
    private KafkaProducerClient kafkaProducerClient;

    public KafkaEventPublisher(KafkaProducerClient kafkaProducerClient){
        this.kafkaProducerClient = kafkaProducerClient;
    }

    @Override
    public void publishEvent(List<OutboxEvent> events) {
        for (OutboxEvent event : events) {
            kafkaProducerClient.sendMessage(event.getEventType().toString(), event.getMessage());
            event.setPublished(true);
        }
    }
}
