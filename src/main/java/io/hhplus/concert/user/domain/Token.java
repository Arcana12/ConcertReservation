package io.hhplus.concert.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private final Integer MAX_QUEUE_SIZE = 20;

    private Long id;
    private Long userId;
    private UUID uuid;
    private TokenStatus tokenStatus;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

}

