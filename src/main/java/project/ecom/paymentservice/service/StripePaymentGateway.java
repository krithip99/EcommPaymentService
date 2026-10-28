package project.ecom.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.PaymentLink;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.ecom.paymentservice.dto.PaymentLinkRequestDto;
import project.ecom.paymentservice.exception.PaymentGatewayException;

import static com.stripe.Stripe.apiKey;

@Component
public class StripePaymentGateway implements PaymentGateway{
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public String getPaymentLink(PaymentLinkRequestDto paymentLinkRequestDto) {
        apiKey = stripeApiKey;
        //Creating Price object
        try{
            PriceCreateParams priceParams =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(paymentLinkRequestDto.getAmount())
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().
                                        setName("Payment Id " + paymentLinkRequestDto.getPaymentId()).build()
                        )
                        .build();
            Price price = Price.create(priceParams);

            //Creating Payment Object
            PaymentLinkCreateParams paymentParams =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(price.getId())
                                            .setQuantity(1L)
                                            .build()
                            ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("http://www.google.com").build())
                                    .build()
                            )
                            .build();

            PaymentLink paymentLink = PaymentLink.create(paymentParams);
            return paymentLink.getUrl();
        }catch(StripeException ex){
            throw new PaymentGatewayException(ex.getMessage());
        }

    }
}
