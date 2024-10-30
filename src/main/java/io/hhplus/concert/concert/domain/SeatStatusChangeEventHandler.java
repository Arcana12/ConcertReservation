package io.hhplus.concert.concert.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeatStatusChangeEventHandler {

    private final SeatService seatService;

    @EventListener
    public void onSeatStatusChangeEvent(SeatStatusChangeEvent event) {
        seatService.checkAndChangeSeatStatus(event.getSeatId());
    }
}
