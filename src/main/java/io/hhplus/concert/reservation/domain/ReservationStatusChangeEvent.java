package io.hhplus.concert.reservation.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReservationStatusChangeEvent extends ApplicationEvent {

    private final Long reservationId;

    public ReservationStatusChangeEvent(Object source, Long reservationId) {
        super(source);
        this.reservationId = reservationId;
    }
}
