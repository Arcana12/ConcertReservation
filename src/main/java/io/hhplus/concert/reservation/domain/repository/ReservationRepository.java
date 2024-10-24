package io.hhplus.concert.reservation.domain.repository;


import io.hhplus.concert.reservation.domain.Reservation;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);
}
