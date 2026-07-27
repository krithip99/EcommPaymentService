package project.ecom.paymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import project.ecom.paymentservice.exception.PaymentGatewayException;

public class RazorPayPaymentGateway implements PaymentGateway{
    private RazorpayClient razorpayClient;

    public RazorPayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String getPaymentGatewayLink(long amount, long orderId, String customerName, String customerEmail, String customerContactNo)  {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 10*60*1000);
        paymentLinkRequest.put("reference_id", orderId);
        paymentLinkRequest.put("description","Payment for "+ orderId);

        JSONObject customer = new JSONObject();
        customer.put("name", customerName);
        customer.put("contact", customerContactNo);
        customer.put("email", customerEmail);
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);

        paymentLinkRequest.put("callback_url","https://www.amazon.in/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = null;
        try {
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        } catch (RazorpayException e) {
            throw new PaymentGatewayException(e.getMessage());
        }
        return payment.toString();
    }
}
