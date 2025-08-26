package com.sun.kong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()            // CSRF 비활성화 (POST 요청 테스트 위해 필요)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 모든 요청 인증 없이 허용
            );
        return http.build();
    }
}