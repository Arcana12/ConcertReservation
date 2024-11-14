package io.hhplus.concert.payment.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentHistEvent extends ApplicationEvent {

    private final Long userId;
    private final Payment payment;

    public PaymentHistEvent(Object source, Payment payment, Long userId) {
        super(source);
        this.payment = payment;
        this.userId = userId;
    }
}
