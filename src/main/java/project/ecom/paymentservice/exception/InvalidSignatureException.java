package project.ecom.paymentservice.exception;

import com.stripe.exception.SignatureVerificationException;

public class InvalidSignatureException extends RuntimeException {
    public InvalidSignatureException(String message, SignatureVerificationException e) {
        super(message, e);
    }

    public InvalidSignatureException(String message){
        super(message);
    }
}
