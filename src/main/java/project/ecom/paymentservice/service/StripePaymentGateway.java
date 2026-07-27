package project.ecom.paymentservice.service;

public class StripePaymentGateway implements PaymentGateway{
    @Override
    public String getPaymentGatewayLink(long amount, long orderId, String customerName, String customerEmail, String customerContactNo){
        return "";
    }

}
