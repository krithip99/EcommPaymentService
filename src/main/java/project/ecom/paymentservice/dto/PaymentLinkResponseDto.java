package project.ecom.paymentservice.dto;

public class PaymentLinkResponseDto {
    private String paymentGateUrl;
    private long paymentId;

    public PaymentLinkResponseDto(String paymentGateUrl, long paymentId) {
        this.paymentGateUrl = paymentGateUrl;
        this.paymentId = paymentId;
    }
}
