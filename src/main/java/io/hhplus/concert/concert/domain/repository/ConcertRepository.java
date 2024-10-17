package io.hhplus.concert.concert.domain.repository;

import io.hhplus.concert.concert.domain.Concert;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcertRepository {
    List<Concert> findByName(String name);

    List<Concert> findByConcertAt(LocalDateTime concertAt);

}
