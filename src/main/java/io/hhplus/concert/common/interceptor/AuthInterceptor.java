package io.hhplus.concert.common.interceptor;

import io.hhplus.concert.common.exception.CustomException;
import io.hhplus.concert.common.exception.ErrorCode;
import io.hhplus.concert.user.domain.RedisQueueService;
import io.hhplus.concert.user.domain.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

//    private final UserService userService;
    private final RedisQueueService redisQueueService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*String tokenUuid = request.getHeader("tokenUuid");

        if (tokenUuid != null) {
            userService.get(UUID.fromString(tokenUuid));
        }*/

        String userUuid = request.getHeader("userUuid");

        if(userUuid != null) {
            redisQueueService.checkQueueStatus(UUID.fromString(userUuid));
        } else{
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        return true;
    }

}
