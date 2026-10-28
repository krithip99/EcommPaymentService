package project.ecom.paymentservice.service;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.ecom.paymentservice.exception.InvalidSignatureException;
import project.ecom.paymentservice.exception.MalformedWebhookException;

import java.util.Optional;
import java.util.Set;

@Component
public class StripeWebhookService {
    @Value("${stripe.webhook.secret}")
    private String webHookSecret;
    private PaymentService paymentService;
    public StripeWebhookService(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    private static final Set<String> SUPPORTED_EVENTS = Set.of(
            "payment_intent.succeeded",
            "payment_intent.payment_failed"
    );

    public void handleWebhook(String payload, String signature) {
        if (payload == null || payload.isBlank()) {
            throw new MalformedWebhookException("Webhook payload is required.");
        }

        if (signature == null || signature.isBlank()) {
            throw new InvalidSignatureException("Missing Stripe-Signature header.");
        }

        if (webHookSecret == null || webHookSecret.isBlank()) {
            throw new IllegalStateException("Stripe webhook secret is not configured.");
        }

        final Event event;
        try {
            event = Webhook.constructEvent(
                    payload,
                    signature,
                    webHookSecret
            );
        } catch (SignatureVerificationException e) {
            throw new InvalidSignatureException("Invalid Stripe Signature", e);
        } catch(Exception e){
            throw new MalformedWebhookException("Unable to process stripe signature", e);
        }

        if(!SUPPORTED_EVENTS.contains(event.getType())){
            return;
        }

        Optional<StripeObject> stripeObject = event.getDataObjectDeserializer().getObject();

        if(!(stripeObject.isPresent()) || !(stripeObject.get() instanceof PaymentIntent paymentIntent)) {
            throw new MalformedWebhookException("Stripe event does not contain a valid PaymentIntent.");
        }

        String paymentId = paymentIntent.getMetadata() == null ? null : paymentIntent.getMetadata().get("paymentId");
        if(paymentId == null){
            throw new MalformedWebhookException("Payment ID not found in payment intent metadata");
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
                paymentService.handlePaymentSucceedWebhook(paymentId, paymentIntent.getAmountReceived());
                break;
            case "payment_intent.payment_failed":
                paymentService.handlePaymentFailedWebhook(paymentId, paymentIntent.getAmountReceived());
                break;
        }
    }
}
