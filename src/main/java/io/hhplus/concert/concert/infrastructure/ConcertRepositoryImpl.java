package io.hhplus.concert.concert.infrastructure;

import io.hhplus.concert.concert.domain.Concert;
import io.hhplus.concert.concert.domain.repository.ConcertRepository;
import io.hhplus.concert.concert.infrastructure.entity.ConcertEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public List<Concert> findByName(String name) {
        return concertJpaRepository.findByName(name).stream()
            .map(ConcertEntity::toDomain)
            .toList();
    }

    @Override
    public List<Concert> findByConcertAt(LocalDateTime concertAt) {
        return  concertJpaRepository.findByConcertAt(concertAt).stream()
            .map(ConcertEntity::toDomain)
            .toList();
    }
}
