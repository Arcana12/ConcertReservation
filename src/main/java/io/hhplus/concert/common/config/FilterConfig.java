package io.hhplus.concert.common.config;

import io.hhplus.concert.common.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LogFilter> loggingFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/*");  // 모든 요청에 대해 적용

        return registrationBean;
    }

}
