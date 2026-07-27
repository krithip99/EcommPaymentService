package project.ecom.paymentservice.dto;

public class PaymentGatewayLinkResponseDto {
    private String paymentGateUrl;

    public PaymentGatewayLinkResponseDto(String paymentGateUrl) {
        this.paymentGateUrl = paymentGateUrl;
    }
}
