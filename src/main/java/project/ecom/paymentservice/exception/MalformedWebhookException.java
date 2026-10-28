package project.ecom.paymentservice.exception;

public class MalformedWebhookException extends RuntimeException {
    public MalformedWebhookException(String message) {
        super(message);
    }

    public MalformedWebhookException(String message, Exception e){
        super(message, e);
    }
}
