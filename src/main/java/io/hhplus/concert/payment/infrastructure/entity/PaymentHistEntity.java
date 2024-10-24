package io.hhplus.concert.payment.infrastructure.entity;

import io.hhplus.concert.payment.domain.PaymentHist;
import io.hhplus.concert.payment.domain.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_hist")
public class PaymentHistEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long paymentId;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime paymentAt;

    public PaymentHistEntity(Long userId, Long paymentId, Long amount, PaymentStatus status, LocalDateTime paymentAt) {
        this.userId = userId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.paymentAt = paymentAt;
    }

    public static PaymentHistEntity fromDomain(PaymentHist paymentHist) {
        return new PaymentHistEntity(paymentHist.getUserId(), paymentHist.getPaymentId(),
            paymentHist.getAmount(), paymentHist.getStatus(), paymentHist.getPaymentAt());
    }

    public PaymentHist toDomain() {
        return new PaymentHist(id, userId, paymentId, amount, status, paymentAt);
    }
}
