package io.hhplus.concert.reservation.application;

import io.hhplus.concert.concert.application.ConcertService;
import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.interfaces.dto.ReservationResponse;
import io.hhplus.concert.user.application.UserService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationFacade {

    private final UserService userService;
    private final ConcertService concertService;
    private final ReservationService reservationService;

    //예약
    @Transactional
    public ReservationResponse createReservation(UUID tokenUuid, UUID userUuid, Long concertId, Long seatId)
        throws Exception {

        userService.checkToken(tokenUuid);
        //좌석 상태 확인 및 변경
        concertService.checkAndChangeSeatStatus(seatId);

        Reservation reservation = reservationService.createReservation(userUuid, concertId, seatId);

        return new ReservationResponse(reservation.getId());
    }
}
