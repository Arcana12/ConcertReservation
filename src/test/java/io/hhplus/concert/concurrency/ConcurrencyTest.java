package io.hhplus.concert.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.concert.domain.Seat;
import io.hhplus.concert.concert.domain.SeatService;
import io.hhplus.concert.concert.domain.SeatStatus;
import io.hhplus.concert.concert.domain.repository.SeatRepository;
import io.hhplus.concert.concert.infrastructure.SeatJpaRepository;
import io.hhplus.concert.concert.infrastructure.entity.SeatEntity;
import io.hhplus.concert.user.domain.User;
import io.hhplus.concert.user.domain.UserService;
import io.hhplus.concert.user.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
public class ConcurrencyTest {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyTest.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatService seatService;

    private User user;
    private Seat seat;

    @BeforeEach
    public void setup() {
        // 테스트용 사용자 생성 및 저장
        user = new User();
        user.setUuid(UUID.randomUUID());
        user.setAmount(10000L);
        userRepository.save(user);

        seat = new Seat();
        seat.setId(1L);
        seat.setStatus(SeatStatus.AVAILABLE);
        seat.setConcertId(1L);
        seat.setSeatNum(101L);
        seatRepository.save(seat);
    }

    @Test
    @DisplayName("잔액 충전에 대한 낙관적 락 동시성 테스트")
    public void testChargeAmountConcurrency() throws Exception {

        AtomicInteger successCount = new AtomicInteger(0); // 성공한 작업 수
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        Runnable task = () -> {
            try {
                User testUser = userRepository.findById(user.getUuid());
                Long newAmount = userService.chargeAmount(testUser.getUuid(), 500L).amount();
                log.info("charge Amount : {}", newAmount);
                successCount.incrementAndGet();
            } catch(Exception e) {
                log.info("Exception : {}", e);
            }
        };

        IntStream.range(0, threadCount).forEach(i -> executorService.submit(task));

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(1, successCount.get());
    }



    @Test
    @DisplayName("잔액 사용에 대한 낙관적 락 동시성 테스트")
    public void testOptimisticLockingWithConcurrency2() throws Exception {

        AtomicInteger successCount = new AtomicInteger(0);  // 성공한 작업 수
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        Runnable task = () -> {
            try {
                User testUser = userRepository.findById(user.getUuid());
                Thread.sleep(50);
                userService.useAmount(testUser, 500L);
                testUser = userRepository.findById(testUser.getUuid()); // 사용자 정보 업데이트
                log.info("amount : {}", testUser.getAmount());
                successCount.incrementAndGet();

            } catch (OptimisticLockingFailureException e) {
                // 낙관적 락 예외 발생 시 로그 출력
                log.info("Optimistic locking failure occurred: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        };

        Future<?>[] futures = new Future<?>[threadCount];

        for (int i = 0; i < threadCount; i++) {
            futures[i] = executorService.submit(task);
        }

        // 모든 작업이 완료될 때까지 대기
        for (Future<?> future : futures) {
            future.get();
        }

        executorService.shutdown();

        //쓰레드 수와 성공한 작업 수 비교
        assertEquals(threadCount, successCount.get());

    }


    @Test
    @DisplayName("예약시 좌석 상태 변경에 대한 비관적 락 테스트")
    void testSeatStatusConcurrency() throws InterruptedException, ExecutionException {

        int threadCount = 10;

        ExecutorService service = Executors.newFixedThreadPool(threadCount);

        List<Callable<Void>> tasks = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        IntStream.range(0, threadCount).forEach(i -> tasks.add(() -> {
            try {
                latch.await();
                seatService.checkAndChangeSeatStatus(seat.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (CustomException e){
                log.info("Custom exception : {}", e.getErrorCode().getMessage());
            }
            return null;
        }));

        latch.countDown();

        List<Future<Void>> futures = service.invokeAll(tasks);

        for (Future<Void> future : futures) {
            future.get();
        }

        //예약 상태 확인
        Optional<Seat> updateSeat = seatRepository.findById(seat.getId());
        Assertions.assertEquals(SeatStatus.RESERVED, updateSeat.get().getStatus());
    }

}
