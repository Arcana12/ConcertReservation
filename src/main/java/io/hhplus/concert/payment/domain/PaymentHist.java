package io.hhplus.concert.payment.domain;

import java.time.LocalDateTime;
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
public class PaymentHist {

    private Long id;
    private Long userId;
    private Long paymentId;
    private Long amount;
    private PaymentStatus status;
    private LocalDateTime paymentAt;
}
