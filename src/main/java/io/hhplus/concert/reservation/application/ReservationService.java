package io.hhplus.concert.reservation.application;

import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.domain.ReservationRepository;
import io.hhplus.concert.reservation.domain.ReservationStatus;
import io.hhplus.concert.reservation.infrastructure.ReservationJpaRepository;
import io.hhplus.concert.user.domain.User;
import io.hhplus.concert.user.domain.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ReservationJpaRepository reservationJpaRepository;

    public Reservation createReservation(UUID uuid, Long concertId, Long seatId) {

        User user = userRepository.findById(uuid);

        Reservation reservation = Reservation.builder()
            .userId(user.getId())
            .concertId(concertId)
            .seatId(seatId)
            .status(ReservationStatus.RESERVED)
            .build();

        return reservationRepository.save(reservation);

    }

    public Reservation checkReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if(reservation.isEmpty()){
            throw new IllegalArgumentException("예약 내역을 찾을 수 없습니다.");
        }
        if(reservation.get().getStatus() != ReservationStatus.RESERVED){
            throw new IllegalArgumentException("예약 상태를 확인해 주세요.");
        }
        return reservation.orElse(null);

    }

}
