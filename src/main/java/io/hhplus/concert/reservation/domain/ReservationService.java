package io.hhplus.concert.reservation.domain;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.reservation.domain.repository.ReservationRepository;
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
            throw new CustomException(ErrorCode.INVALID_RESERVATION);
        }
        if(reservation.get().getStatus() != ReservationStatus.RESERVED){
            throw new CustomException(ErrorCode.ALREADY_RESERVED);
        }
        return reservation.orElse(null);

    }

    public Reservation changeReservationStatus(Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);

        if(reservation.isEmpty()){
            throw new CustomException(ErrorCode.INVALID_RESERVATION);
        }
        Reservation changeReservation = reservation.get();
        changeReservation.setStatus(ReservationStatus.SOLD);

        reservationRepository.save(changeReservation);

        return changeReservation;
    }

}
