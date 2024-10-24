package io.hhplus.concert.concert.domain.repository;

import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import java.util.List;
import java.util.Optional;

public interface SeatRepository {
    List<Seat> findByConcertIdAndStatus(Long concertId, SeatStatus status);

    Optional<Seat> findById(Long id);

    void save(Seat seat);

    List<Seat> findByStatus(SeatStatus status);
}
