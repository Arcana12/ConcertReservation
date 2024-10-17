package io.hhplus.concert.payment.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Payment {

    private Long id;
    private Long reservationId;
    private Long amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}
