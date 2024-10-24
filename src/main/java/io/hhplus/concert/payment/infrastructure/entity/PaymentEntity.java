package io.hhplus.concert.payment.infrastructure.entity;

import io.hhplus.concert.payment.domain.Payment;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long reservationId;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    public PaymentEntity(Long reservationId, Long amount, PaymentStatus status) {
        this.reservationId = reservationId;
        this.amount = amount;
        this.status = status;
    }

    public static PaymentEntity fromDomain(Payment payment){
        return new PaymentEntity(payment.getReservationId(), payment.getAmount(), payment.getStatus());
    }

    public Payment toDomain(){
        return new Payment(id, reservationId, amount, status, createdAt);
    }
}
