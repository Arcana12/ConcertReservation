package io.hhplus.concert.user.infrastructure;

import io.hhplus.concert.user.domain.TokenStatus;
import io.hhplus.concert.user.infrastructure.entity.TokenEntity;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    @Query("SELECT t FROM TokenEntity t WHERE t.tokenStatus = 'ISSUED'")
    List<TokenEntity> findByAllStatus();

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.id = :id")
    void deleteById(@NonNull Long id);

    @Query("SELECT t FROM TokenEntity t WHERE t.tokenStatus = 'PENDING' ORDER BY t.createdAt DESC")
    List<TokenEntity> findPendingStatusTokens();

    long countByTokenStatus(TokenStatus tokenStatus);

    TokenEntity findByUuid(UUID tokenUuid);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.userId = :userId")
    void deleteByUserId(Long userId);

}
