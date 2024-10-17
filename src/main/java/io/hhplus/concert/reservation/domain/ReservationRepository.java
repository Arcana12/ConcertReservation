package io.hhplus.concert.reservation.domain;


import io.hhplus.concert.reservation.infrastructure.entity.ReservationEntity;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);
}
