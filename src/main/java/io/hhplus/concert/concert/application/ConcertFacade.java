package io.hhplus.concert.concert.application;

import io.hhplus.concert.concert.domain.Concert;
import io.hhplus.concert.concert.interfaces.dto.ConcertDateResponse;
import io.hhplus.concert.concert.interfaces.dto.ConcertSeatResponse;
import io.hhplus.concert.user.application.UserService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertFacade {

    private final UserService userService;
    private final ConcertService concertService;

    //예약 가능한 날짜 조회
    public ConcertDateResponse getConcertDate(UUID uuid, String concertName) throws Exception {
        userService.checkToken(uuid);

        return new ConcertDateResponse(concertService.getConcertDate(concertName).stream()
            .map(Concert::getConcertAt)
            .toList());
    }

    //예약 가능한 좌석 조회
    public ConcertSeatResponse getSeat(UUID uuid, LocalDateTime date) throws Exception {
        userService.checkToken(uuid);

        return new ConcertSeatResponse(concertService.getSeat(date));
    }


}
