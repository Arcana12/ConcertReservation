package io.hhplus.concert.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);



        try {
            // 핸들러/컨트롤러 실행
            chain.doFilter(requestWrapper, responseWrapper);

            //요청 처리 중 장애가 발생해도 request 로그는 남아있도록 해당 위치에 작성
            String requestBody = new String(requestWrapper.getContentAsByteArray());
            log.info("Request Body: {}", requestBody);

        } catch (Exception e){
            log.error("서버 에러가 발생했습니다 : {}", e.getMessage());
        } finally{


            String responseBody = new String(responseWrapper.getContentAsByteArray());
            log.info("Response Body: {}", responseBody);

            responseWrapper.copyBodyToResponse();
        }
    }

}
