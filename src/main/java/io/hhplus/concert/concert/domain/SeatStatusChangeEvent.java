package io.hhplus.concert.concert.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SeatStatusChangeEvent extends ApplicationEvent {

    private final Long seatId;

    public SeatStatusChangeEvent(Object source, Long seatId) {
        super(source);
        this.seatId = seatId;
    }

}