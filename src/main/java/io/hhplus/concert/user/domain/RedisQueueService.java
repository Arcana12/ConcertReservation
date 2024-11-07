package io.hhplus.concert.user.domain;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.user.domain.repository.UserRepository;
import io.hhplus.concert.user.interfaces.dto.QueueResponse;
import io.hhplus.concert.user.interfaces.dto.TokenRequest;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisQueueService {

    private final UserRepository userRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void init() {
        // StringRedisTemplate에서 ZSetOperations 인스턴스를 가져옴
        zSetOps = stringRedisTemplate.opsForZSet();
    }

    // 대기열 추가
    public QueueResponse addToWaitQueue(UUID uuid) {
        User user = userRepository.findById(uuid);
        if(user == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        zSetOps.add("waitQueue", user.getId().toString(), Instant.now().toEpochMilli());
        return new QueueResponse(zSetOps.rank("waitQueue", user.getId().toString()));
    }

    // 토큰 발급
    public void dequeueWithExpiry() {
        // ZRANGE 명령을 통해 0에서 19 인덱스까지 조회
        Set<String> queueData = stringRedisTemplate.opsForZSet().range("waitQueue", 0, 19);

        if (queueData != null) {
            for (String userId : queueData) {
                // 각 데이터를 "set" 명령으로 저장하고 만료 시간 설정
                stringRedisTemplate.opsForValue().set(userId, userId);
                stringRedisTemplate.expire(userId, 10, TimeUnit.MINUTES);
                log.info("dequeueWithExpiry : {}", userId);
                // 저장된 데이터는 waitQueue에서 제거
                zSetOps.remove("waitQueue", userId);
            }
        }
    }

    // 대기열 번호 확인
    public QueueResponse getQueuePosition(UUID uuid) {
        User user = userRepository.findById(uuid);
        if(user == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        return new QueueResponse(zSetOps.rank("waitQueue", user.getId().toString()));
    }

    // 토큰 제거
    public void removeQueue(Long id) {
        String value = stringRedisTemplate.opsForValue().get(id);
        if(value == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        stringRedisTemplate.delete(value);
    }

    // 토큰 확인
    public void checkQueueStatus(UUID userUuid) {
        User user = userRepository.findById(userUuid);
        if(user == null) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        String value = stringRedisTemplate.opsForValue().get(user.getId());
        if (value == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
