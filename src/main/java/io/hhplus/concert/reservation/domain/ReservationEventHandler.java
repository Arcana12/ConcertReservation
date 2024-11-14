package io.hhplus.concert.reservation.domain;

import io.hhplus.concert.payment.domain.PaymentHistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationEventHandler {

    private final ReservationService reservationService;

    @Async
    @EventListener
    public void statusChangeEvent(PaymentHistEvent event) {
        reservationService.changeReservationStatus(event.getUserId());
    }
}
