package io.hhplus.concert.payment.domain;

import io.hhplus.concert.payment.domain.repository.PaymentHistRepository;
import io.hhplus.concert.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentHistRepository paymentHistRepository;

    public Payment createPayment(Long reservationId, Long amount){

        Payment payment = Payment.builder()
            .reservationId(reservationId)
            .amount(amount)
            .status(PaymentStatus.SUCCESS)
            .build();
        return paymentRepository.save(payment);
    }

    public PaymentHist savePaymentHist(Payment payment, Long userId) {

        PaymentHist paymentHist = PaymentHist.builder()
            .paymentId(payment.getId())
            .userId(userId)
            .status(payment.getStatus())
            .amount(payment.getAmount())
            .paymentAt(payment.getCreatedAt())
            .build();
        return paymentHistRepository.save(paymentHist);
    }
}
