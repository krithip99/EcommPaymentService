package project.ecom.paymentservice.service;

public interface WebhookService {
    void handleWebhook(String payload, String signature);
}
