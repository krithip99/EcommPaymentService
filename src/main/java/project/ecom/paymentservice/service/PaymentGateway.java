package project.ecom.paymentservice.service;

import com.razorpay.RazorpayException;

public interface PaymentGateway {
    String getPaymentGatewayLink(long amount, long orderId, String customerName, String customerEmail, String customerContactNo);
}
