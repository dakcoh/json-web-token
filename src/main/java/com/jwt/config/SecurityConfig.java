package com.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))  // ← H2 UI가 iframe을 사용함
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화: JWT 사용 환경에서는 불필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**", "/h2-console/**").permitAll() // 인증 없이 접근 가능한 엔드포인트
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless 설정
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 패스워드 암호화를 위한 인코더 빈
    }
}
