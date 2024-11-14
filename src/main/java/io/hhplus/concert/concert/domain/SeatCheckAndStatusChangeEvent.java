package io.hhplus.concert.concert.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SeatCheckAndStatusChangeEvent extends ApplicationEvent {

    private final Long seatId;

    public SeatCheckAndStatusChangeEvent(Object source, Long seatId) {
        super(source);
        this.seatId = seatId;
    }

}