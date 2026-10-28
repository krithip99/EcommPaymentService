package project.ecom.paymentservice.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import project.ecom.paymentservice.dto.PaymentLinkRequestDto;
import project.ecom.paymentservice.exception.PaymentGatewayException;

@Component
public class RazorPayPaymentGateway implements PaymentGateway{
    private RazorpayClient razorpayClient;

    public RazorPayPaymentGateway(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public String getPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto)  {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",paymentLinkRequestDto.getAmount());
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("expire_by", System.currentTimeMillis() + 10*60*1000);
        paymentLinkRequest.put("reference_id", paymentLinkRequestDto.getPaymentId());
        paymentLinkRequest.put("description","Payment for "+ paymentLinkRequestDto.getOrderId());

        JSONObject customer = new JSONObject();
        customer.put("name", paymentLinkRequestDto.getCustomerName());
        customer.put("contact", paymentLinkRequestDto.getContactNo());
        customer.put("email", paymentLinkRequestDto.getCustomerEmail());
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
