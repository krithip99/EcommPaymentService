package project.ecom.paymentservice.service;

import project.ecom.paymentservice.dto.PaymentLinkRequestDto;

public interface PaymentGateway {
    String getPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto);
}
