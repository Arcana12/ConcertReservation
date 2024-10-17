package io.hhplus.concert.reservation.infrastructure;

import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.domain.ReservationRepository;
import io.hhplus.concert.reservation.infrastructure.entity.ReservationEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public Reservation save(Reservation reservation){
        return reservationJpaRepository.save(ReservationEntity.fromDomain(reservation)).toDomain();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationJpaRepository.findById(id).map(ReservationEntity::toDomain);
    }
}
