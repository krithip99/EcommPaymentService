package project.ecom.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.ecom.paymentservice.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentGatewayException.class)
    public ResponseEntity<ErrorResponseDto> handlePaymentGatewayException(PaymentGatewayException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto("Payment gateway error: " + ex.getMessage()));
    }
}
