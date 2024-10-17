package io.hhplus.concert.concert.application;

import io.hhplus.concert.concert.domain.Concert;
import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatStatus;
import io.hhplus.concert.concert.domain.repository.ConcertRepository;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;


    public List<Concert> getConcertDate(String concertName) {
        return concertRepository.findByName(concertName);
    }


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

        Seat seat = seatRepository.findByIdForUpdate(id)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));

        if(seat.getStatus() != SeatStatus.AVAILABLE){
            throw new RuntimeException("해당 좌석은 예약할 수 없습니다.");
        }

        seat.setStatus(SeatStatus.RESERVED);
        seatRepository.save(seat);

    }

    public void expiredReserve(){
        List<Seat> seatList = seatRepository.findByStatus(SeatStatus.RESERVED);
        for(Seat seat : seatList) {
            if(seat.isExpire(seat)){
                seat.setStatus(SeatStatus.AVAILABLE);
                seatRepository.save(seat);
            }
        }
    }

    public void changeStatus(Long id){
        Seat seat = seatRepository.findByIdForUpdate(id)
            .orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));
        seat.setStatus(SeatStatus.OCCUPIED);
        seatRepository.save(seat);
    }
}
