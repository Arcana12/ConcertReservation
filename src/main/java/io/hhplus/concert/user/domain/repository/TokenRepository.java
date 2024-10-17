package io.hhplus.concert.user.domain.repository;

import io.hhplus.concert.user.domain.Token;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository {

    Optional<Token> findByUserId(Long id);

    void save(Token token);

    List<Token> findByStateIssued();

    void deleteToken(Long id);

    List<Token> findPendingStatusTokens();

    Token findByTokenUuid(UUID tokenUuid);
}
