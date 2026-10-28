package project.ecom.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ecom.paymentservice.dto.PaymentLinkResponseDto;
import project.ecom.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("gateway/link/{orderId}")
    public ResponseEntity<PaymentLinkResponseDto> getPaymentLink(@PathVariable int orderId){
        return ResponseEntity.ok(paymentService.getPaymentLink(orderId));
    }

    @GetMapping("/payment/status/{id}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.getPaymentStatus(id));
    }
}
