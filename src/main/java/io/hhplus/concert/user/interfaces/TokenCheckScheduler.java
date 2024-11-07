package io.hhplus.concert.user.interfaces;

import io.hhplus.concert.user.domain.RedisQueueService;
import io.hhplus.concert.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCheckScheduler {

    private final UserService userService;
    private final RedisQueueService redisQueueService;

    /*//토큰 만료
    @Scheduled(fixedRate = 5000)
    public void expiredToken(){
        userService.tokenExpire();
    }

    //대기열 관리
    @Scheduled(fixedRate = 5000)
    public void queueToToken(){
        userService.queueToIssuedToken();
    }*/

    @Scheduled(cron = "0/10 * * * * *")
    public void dequeueWithExpiry(){
        redisQueueService.dequeueWithExpiry();
    }

}
