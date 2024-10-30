package io.hhplus.concert.concert.application;

import io.hhplus.concert.concert.domain.Concert;
import io.hhplus.concert.concert.domain.ConcertService;
import io.hhplus.concert.concert.domain.SeatService;
import io.hhplus.concert.concert.interfaces.dto.ConcertDateResponse;
import io.hhplus.concert.concert.interfaces.dto.ConcertSeatResponse;
import io.hhplus.concert.user.domain.UserService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertFacade {

    private final ConcertService concertService;
    private final SeatService seatService;

    //예약 가능한 날짜 조회
    public ConcertDateResponse getConcertDate(String concertName) {

        return new ConcertDateResponse(concertService.getConcertDate(concertName).stream()
            .map(Concert::getConcertAt)
            .toList());
    }

    //예약 가능한 좌석 조회
    public ConcertSeatResponse getSeat(LocalDateTime date) {

        return new ConcertSeatResponse(seatService.getSeat(date));
    }


}
