package io.hhplus.concert.user.infrastructure.entity;

import io.hhplus.concert.user.domain.Token;
import io.hhplus.concert.user.domain.TokenStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EnableJpaAuditing
public class TokenEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private UUID uuid;
    private TokenStatus tokenStatus;
    private LocalDateTime expiredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public Token toDomain() {
        return new Token(id, userId, uuid, tokenStatus, expiredAt, createdAt);
    }

    public static TokenEntity fromDomain(Token token) {
        return new TokenEntity(token.getId(), token.getUserId(), token.getUuid(), token.getTokenStatus(),
            token.getExpiredAt(), token.getCreatedAt());
    }
}
