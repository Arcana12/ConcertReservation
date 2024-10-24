package io.hhplus.concert.user.infrastructure;

import io.hhplus.concert.user.domain.TokenStatus;
import io.hhplus.concert.user.infrastructure.entity.TokenEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    @Query("SELECT t FROM TokenEntity t WHERE t.tokenStatus = 'ISSUED'")
    List<TokenEntity> findByAllStatus();

    void deleteById(@Param("id") Long id);

    @Query("SELECT t FROM TokenEntity t WHERE t.tokenStatus = 'PENDING' ORDER BY t.createdAt DESC")
    List<TokenEntity> findPendingStatusTokens();

    long countByTokenStatus(TokenStatus tokenStatus);

    Optional<TokenEntity> findByUuid(UUID tokenUuid);

    void deleteByUserId(Long userId);

}
