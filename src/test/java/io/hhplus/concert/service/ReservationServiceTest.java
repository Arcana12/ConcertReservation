package io.hhplus.concert.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.hhplus.concert.reservation.domain.ReservationService;
import io.hhplus.concert.reservation.domain.Reservation;
import io.hhplus.concert.reservation.domain.repository.ReservationRepository;
import io.hhplus.concert.reservation.domain.ReservationStatus;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;  // checkReservation 메서드가 속해 있는 서비스

    @Test
    @DisplayName("예약을 정상적으로 조회할 때 성공해야 한다.")
    void testCheckReservation_Success() {
        // Given
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.RESERVED);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // When
        Reservation result = reservationService.checkReservation(reservationId);

        // Then
        assertNotNull(result);
        assertEquals(reservationId, result.getId());
        assertEquals(ReservationStatus.RESERVED, result.getStatus());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    @DisplayName("예약을 찾을 수 없을 때 예외가 발생해야 한다.")
    void testCheckReservation_ReservationNotFound() {
        // Given
        Long reservationId = 1L;

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            reservationService.checkReservation(reservationId)
        );
        assertEquals("예약 내역을 찾을 수 없습니다.", exception.getMessage());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    @DisplayName("예약 상태가 RESERVED가 아닐 때 예외가 발생해야 한다.")
    void testCheckReservation_InvalidStatus() {
        // Given
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.SOLD);  // 예약 상태가 RESERVED가 아님

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            reservationService.checkReservation(reservationId)
        );
        assertEquals("예약 상태를 확인해 주세요.", exception.getMessage());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

}
