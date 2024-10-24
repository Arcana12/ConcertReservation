package io.hhplus.concert.concert.domain;

import io.hhplus.concert.concert.domain.repository.ConcertRepository;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;


    public List<Concert> getConcertDate(String concertName) {
        return concertRepository.findByName(concertName);
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


}
