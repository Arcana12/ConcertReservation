package io.hhplus.concert.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        CachingRequestBodyFilter wrappedRequest = new CachingRequestBodyFilter((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);


        try {
            String requestBody = new String(wrappedRequest.getCachedBody(), StandardCharsets.UTF_8);

            log.info("Request body: {}", requestBody);

            // 핸들러/컨트롤러 실행
            chain.doFilter(wrappedRequest, responseWrapper);


        } catch (Exception e){
            log.error("서버 에러가 발생했습니다 : {}", e.getMessage());
        } finally{


            String responseBody = new String(responseWrapper.getContentAsByteArray());
            log.info("Response Body: {}", responseBody);

            responseWrapper.copyBodyToResponse();
        }
    }

}
