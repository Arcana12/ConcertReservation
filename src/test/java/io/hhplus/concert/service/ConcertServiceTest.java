package io.hhplus.concert.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import io.hhplus.concert.concert.application.ConcertService;
import io.hhplus.concert.concert.domain.Concert;
import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import io.hhplus.concert.concert.domain.repository.ConcertRepository;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import io.hhplus.concert.user.application.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ConcertService concertService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("특정 시간에 따른 사용 가능한 좌석을 가져온다")
    public void getSeat() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now();

        Concert concert = new Concert();
        concert.setId(1L);

        List<Concert> concertList = List.of(concert);
        doReturn(concertList).when(concertRepository).findByConcertAt(dateTime);

        Seat seat = new Seat();
        seat.setId(1L);
        seat.setStatus(SeatStatus.AVAILABLE);

        List<Seat> seatList = List.of(seat);
        doReturn(seatList).when(seatRepository).findByConcertIdAndStatus(concert.getId(), SeatStatus.AVAILABLE);

        // When
        List<Seat> allSeats = concertService.getSeat(dateTime);

        // Then
        verify(concertRepository).findByConcertAt(dateTime);
        verify(seatRepository).findByConcertIdAndStatus(concert.getId(), SeatStatus.AVAILABLE);
        assertEquals(1, allSeats.size());
        assertEquals(seat.getId(), allSeats.get(0).getId());
        assertEquals(SeatStatus.AVAILABLE, allSeats.get(0).getStatus());
    }

    @Test
    @DisplayName("사용 가능한 좌석의 상태를 예약으로 변경한다")
    public void checkAndChangeSeatStatus() {
        // Given
        Long id = 1L;

        Seat seat = new Seat();
        seat.setId(id);
        seat.setStatus(SeatStatus.AVAILABLE);

        doReturn(Optional.of(seat)).when(seatRepository).findByIdForUpdate(id);

        // When
        concertService.checkAndChangeSeatStatus(id);

        // Then
        verify(seatRepository).findByIdForUpdate(id);
        assertEquals(SeatStatus.RESERVED, seat.getStatus());
        verify(seatRepository).save(seat);
    }

    @Test
    @DisplayName("사용할 수 없는 좌석 상태로 예약 요청하면 에러가 발생한다")
    public void checkAndChangeSeatStatus_error() {
        // Given
        Long id = 1L;

        Seat seat = new Seat();
        seat.setId(id);
        seat.setStatus(SeatStatus.RESERVED);

        doReturn(Optional.of(seat)).when(seatRepository).findByIdForUpdate(id);

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> concertService.checkAndChangeSeatStatus(id));
        assertEquals("해당 좌석은 예약할 수 없습니다.", exception.getMessage());
    }

}
