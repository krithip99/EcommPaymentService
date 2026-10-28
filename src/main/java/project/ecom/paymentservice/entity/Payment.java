package project.ecom.paymentservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Payment extends BaseClass {

    private long orderId;
    private long amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentGatewayName paymentGatewayName;
    private String transactionId;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public PaymentGatewayName getPaymentGatewayName() {
        return paymentGatewayName;
    }

    public void setPaymentGatewayName(PaymentGatewayName paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
