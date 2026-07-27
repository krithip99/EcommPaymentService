package project.ecom.paymentservice.service;

import com.razorpay.RazorpayException;
import project.ecom.paymentservice.dto.PaymentGatewayLinkResponseDto;

public interface PaymentService {
    PaymentGatewayLinkResponseDto getPaymentGatewayLink(int orderId);
}
