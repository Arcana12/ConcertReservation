package io.hhplus.concert.user.interfaces.dto;

import java.util.UUID;

public record AmountChargeRequest (
    UUID userUuid,
    Long amount
){

}
