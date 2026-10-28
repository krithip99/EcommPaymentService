package project.ecom.paymentservice.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import project.ecom.paymentservice.client.OrderServiceClient;
import project.ecom.paymentservice.dto.OrderDto;
import project.ecom.paymentservice.dto.PaymentLinkRequestDto;
import project.ecom.paymentservice.dto.PaymentLinkResponseDto;
import project.ecom.paymentservice.entity.EventType;
import project.ecom.paymentservice.entity.Payment;
import project.ecom.paymentservice.entity.PaymentStatus;
import project.ecom.paymentservice.entity.SourceProvider;
import project.ecom.paymentservice.event.Event;
import project.ecom.paymentservice.event.PaymentEvent;
import project.ecom.paymentservice.exception.PaymentNotFoundException;
import project.ecom.paymentservice.repository.PaymentRepository;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService{

    private PaymentGateway paymentGateway;
    private OrderServiceClient orderServiceClient;
    private PaymentRepository paymentRepository;
    private OutboxEventService outboxEventService;

    public PaymentServiceImpl(PaymentGateway paymentGateway, OrderServiceClient orderServiceClient, PaymentRepository paymentRepository,
                              OutboxEventService outboxEventService) {
        this.paymentGateway = paymentGateway;
        this.orderServiceClient = orderServiceClient;
        this.paymentRepository = paymentRepository;
        this.outboxEventService = outboxEventService;
    }

    @Override
    public PaymentLinkResponseDto getPaymentLink(int orderId) {
        OrderDto order = orderServiceClient.getOrderDetails(orderId);

        Payment payment = new Payment();
        payment.setOrderId(order.getOrderId());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment = paymentRepository.save(payment);

        PaymentLinkRequestDto paymentLinkRequestDto = new PaymentLinkRequestDto(payment.getId(), order.getTotalAmount(), order.getOrderId(), order.getCustomerName(), order.getCustomerEmail(), order.getCustomerContactNo());
        String paymentGateUrl = paymentGateway.getPaymentLink(paymentLinkRequestDto);
        return new PaymentLinkResponseDto(paymentGateUrl, payment.getId());
    }


    @Override
    public String getPaymentStatus(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        return payment.getPaymentStatus().toString();
    }

    @Override
    @Transactional
    public void handlePaymentSucceedWebhook(String stripeEventId, String paymentId, Long amountReceived, SourceProvider sourceProvider) {
        Payment payment = paymentRepository.findById(Long.valueOf(paymentId)).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        if(payment.getPaymentStatus() == PaymentStatus.SUCCESS){
            return;
        }
        Event event = new PaymentEvent(
                UUID.randomUUID(),
                payment.getId(),
                payment.getOrderId(),
                amountReceived,
                EventType.PAYMENT_SUCCEED
        );

        outboxEventService.saveOutboxEvent(event, sourceProvider, Long.valueOf(stripeEventId));

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

    }

    @Override
    @Transactional
    public void handlePaymentFailedWebhook(String stripeEventId, String paymentId, Long amountReceived, SourceProvider sourceProvider) {
        Payment payment = paymentRepository.findById(Long.valueOf(paymentId)).orElseThrow(() -> new PaymentNotFoundException("Payment not found"));
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);

        PaymentEvent event = new PaymentEvent(
                UUID.randomUUID(),
                payment.getId(),
                payment.getOrderId(),
                amountReceived,
                EventType.PAYMENT_FAILED
        );

        outboxEventService.saveOutboxEvent(event, sourceProvider, Long.valueOf(stripeEventId));
    }
}

