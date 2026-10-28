package project.ecom.paymentservice.service;

import project.ecom.paymentservice.dto.PaymentLinkResponseDto;

public interface PaymentService {
    PaymentLinkResponseDto getPaymentLink(int orderId);

    String getPaymentStatus(Long id);

    void handlePaymentSucceedWebhook(String paymentId, Long amountReceived);

    void handlePaymentFailedWebhook(String paymentId, Long amountReceived);
}
