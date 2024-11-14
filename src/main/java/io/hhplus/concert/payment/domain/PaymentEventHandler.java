package io.hhplus.concert.payment.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final PaymentService paymentService;

    @Async
    @EventListener
    public void savePaymentHist(PaymentHistEvent event) {
        paymentService.savePaymentHist(event.getPayment(), event.getUserId());
    }
}
