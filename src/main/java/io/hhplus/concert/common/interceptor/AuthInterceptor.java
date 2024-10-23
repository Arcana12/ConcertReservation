package io.hhplus.concert.common.interceptor;

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

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenUuid = request.getHeader("tokenUuid");

        if (tokenUuid != null) {
            userService.checkToken(UUID.fromString(tokenUuid));
        }

        return true;
    }

}
