package io.hhplus.concert.concert.infrastructure;

import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import io.hhplus.concert.concert.infrastructure.entity.SeatEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findByConcertIdAndStatus(Long concertId, SeatStatus status) {
        return seatJpaRepository.findByConcertIdAndStatus(concertId, status).stream()
            .map(SeatEntity::toDomain)
            .toList();
    }

    @Override
    public Optional<Seat> findByIdForUpdate(Long id) {
        return seatJpaRepository.findById(id).map(SeatEntity::toDomain);
    }

    @Override
    public void save(Seat seat) {
        seatJpaRepository.save(SeatEntity.fromDomain(seat));
    }

    @Override
    public List<Seat> findByStatus(SeatStatus status) {
        return seatJpaRepository.findByStatus(status)
            .stream().map(SeatEntity::toDomain)
            .toList();
    }
}
