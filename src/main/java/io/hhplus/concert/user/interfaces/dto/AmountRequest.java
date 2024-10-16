package io.hhplus.concert.user.interfaces.dto;

import java.util.UUID;

public record AmountRequest
    (
        UUID userUuid,
        Long amount
    ){
}
