package io.hhplus.concert.user.domain.repository;

import io.hhplus.concert.user.domain.Token;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository {

    Optional<Token> findByUserId(Long id);

    void save(Token token);

    List<Token> findByStateIssued();

    void deleteToken(Long id);

    List<Token> findPendingStatusTokens();

    Optional<Token> findByTokenUuid(UUID tokenUuid);
}
