package project.ecom.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    private long paymentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long orderId;
    private long amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentGatewayName paymentGatewayName;
    private String transactionId;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
}
