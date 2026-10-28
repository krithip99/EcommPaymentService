package project.ecom.paymentservice.service;

import project.ecom.paymentservice.dto.PaymentLinkResponseDto;
import project.ecom.paymentservice.entity.SourceProvider;

public interface PaymentService {
    PaymentLinkResponseDto getPaymentLink(int orderId);

    String getPaymentStatus(Long id);

    void handlePaymentSucceedWebhook(String stripeEventId, String paymentId, Long amountReceived, SourceProvider stripe);

    void handlePaymentFailedWebhook(String stripeEventId, String paymentId, Long amountReceived, SourceProvider stripe);
}
