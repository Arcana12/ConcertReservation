package io.hhplus.concert.concert.domain;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.concert.domain.repository.ConcertRepository;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;

    public List<Seat> getSeat(LocalDateTime dateTime) {

        List<Concert> concertList = concertRepository.findByConcertAt(dateTime);

        List<Seat> allSeats = new ArrayList<>();
        for(Concert concert : concertList) {
            List<Seat> seatList = seatRepository.findByConcertIdAndStatus(concert.getId(), SeatStatus.AVAILABLE);
            allSeats.addAll(seatList.stream().toList());
        }

        return allSeats;
    }

    @Transactional
    public void checkAndChangeSeatStatus(Long id){

        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SEAT));

        if(seat.getStatus() != SeatStatus.AVAILABLE){
            throw new CustomException(ErrorCode.ALREADY_RESERVED);
        }

        log.info("seat : {}", seat.getUpdatedAt());
        seat.setStatus(SeatStatus.RESERVED);
        seat.setUpdatedAt(LocalDateTime.now());
        seatRepository.save(seat);

    }

    public void changeStatus(Long id){
        Seat seat = seatRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SEAT));
        seat.setStatus(SeatStatus.OCCUPIED);
        seatRepository.save(seat);
    }
}
