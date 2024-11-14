package io.hhplus.concert.concert.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SeatEventHandler {

    private final SeatService seatService;

    @TransactionalEventListener
    public void seatCheckAndStatusChangeEvent(SeatCheckAndStatusChangeEvent event) {
        seatService.checkAndChangeSeatStatus(event.getSeatId());
    }

    @Async
    @EventListener
    public void seatStatusChangeEvent(SeatStatusChangeEvent event){
        seatService.changeStatus(event.getSeatId());
    }
}
