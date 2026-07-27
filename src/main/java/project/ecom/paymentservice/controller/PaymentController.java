package project.ecom.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ecom.paymentservice.dto.PaymentGatewayLinkResponseDto;
import project.ecom.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("gateway/link/{orderId}")
    public ResponseEntity<PaymentGatewayLinkResponseDto> getPaymentGatewayLink(@PathVariable int orderId){
        return ResponseEntity.ok(paymentService.getPaymentGatewayLink(orderId));
    }
}
