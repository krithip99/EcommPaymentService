package project.ecom.paymentservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.ecom.paymentservice.dto.OrderDto;

@Component
public class OrderServiceClient {
    private RestTemplate restTemplate;
    private final String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate, @Value("${service.order.url}") String orderServiceUrl) {
        this.orderServiceUrl = orderServiceUrl;
        this.restTemplate = restTemplate;
    }

    public OrderDto getOrderDetails(int orderId) {
        String url = orderServiceUrl + "/" + orderId;
        return restTemplate.getForObject(orderServiceUrl, OrderDto.class);
    }
}
