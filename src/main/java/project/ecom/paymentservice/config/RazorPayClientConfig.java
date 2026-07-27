package project.ecom.paymentservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayClientConfig {
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Bean
    public com.razorpay.RazorpayClient getRazorpayClient() throws com.razorpay.RazorpayException {
        return new com.razorpay.RazorpayClient(razorpayKeyId, razorpayKeySecret);
    }

}
