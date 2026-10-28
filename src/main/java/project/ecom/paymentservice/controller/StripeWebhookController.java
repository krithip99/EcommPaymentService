package project.ecom.paymentservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import project.ecom.paymentservice.service.StripeWebhookService;

@RestController
public class StripeWebhookController {

    private StripeWebhookService webhookService;

    public StripeWebhookController(StripeWebhookService stripeWebhookService) {
        this.webhookService = stripeWebhookService;
    }

    @PostMapping("/webhook/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String signature) {
        webhookService.handleWebhook(payload, signature);
        return ResponseEntity.ok("Webhook received successfully");
    }
}
