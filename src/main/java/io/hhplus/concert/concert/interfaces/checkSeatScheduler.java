package io.hhplus.concert.concert.interfaces;

import io.hhplus.concert.concert.domain.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class checkSeatScheduler {

    private final ConcertService concertService;

    //예약 후 결제 시간 만료
    @Scheduled(fixedRate = 5000)
    public void expiredReserve(){
        concertService.expiredReserve();
    }

}
