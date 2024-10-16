package io.hhplus.concert.user.infrastructure;


import io.hhplus.concert.user.domain.Token;
import io.hhplus.concert.user.domain.repository.TokenRepository;
import io.hhplus.concert.user.infrastructure.entity.TokenEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<Token> findByUserId(Long id) {
        return tokenJpaRepository.findById(id).map(TokenEntity::toDomain);
    }

    @Override
    public void save(Token token) {
        TokenEntity tokenEntity = TokenEntity.fromDomain(token);
        tokenJpaRepository.save(tokenEntity).toDomain();
    }

    @Override
    public List<Token> findByStateIssued() {
        return tokenJpaRepository.findByAllStatus().stream()
            .map(TokenEntity::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Page<Token> findPendingStatusTokens(Pageable pageable) {
        Page<TokenEntity> pageTokens = tokenJpaRepository.findPendingStatusTokens(pageable);
        return pageTokens.map(TokenEntity::toDomain);
    }

    @Override
    public void deleteToken(Long id) {
        tokenJpaRepository.deleteById(id);
    }

    @Override
    public Token findByTokenUuid(UUID tokenUuid) {
        return tokenJpaRepository.findByTokenUuid(tokenUuid).toDomain();
    }
}
