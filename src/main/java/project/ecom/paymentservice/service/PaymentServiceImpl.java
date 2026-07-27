package project.ecom.paymentservice.service;

import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;
import project.ecom.paymentservice.client.OrderServiceClient;
import project.ecom.paymentservice.dto.OrderDto;
import project.ecom.paymentservice.dto.PaymentGatewayLinkResponseDto;

@Service
public class PaymentServiceImpl implements PaymentService{

    private PaymentGateway paymentGateway;
    private OrderServiceClient orderServiceClient;

    @Override
    public PaymentGatewayLinkResponseDto getPaymentGatewayLink(int orderId) {
        OrderDto order = orderServiceClient.getOrderDetails(orderId);
        String paymentGateUrl = paymentGateway.getPaymentGatewayLink(order.getAmount(), order.getOrderId(), order.getCustomerName(), order.getCustomerEmail(), order.getCustomerContactNo());
        return new PaymentGatewayLinkResponseDto(paymentGateUrl);
    }

}
