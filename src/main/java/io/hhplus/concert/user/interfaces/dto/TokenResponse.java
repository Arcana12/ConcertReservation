package io.hhplus.concert.user.interfaces.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TokenResponse(
    UUID tokenUuid,
    LocalDateTime expiredAt,
    Long totalWaiting
) {

}
