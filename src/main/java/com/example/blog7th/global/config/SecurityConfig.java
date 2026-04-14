package com.example.blog7th.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF 보안 비활성화 (API 테스트 시 필수)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. H2 콘솔 및 프레임 관련 설정
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )

                // 3. 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()    // H2 DB 허용
                        .requestMatchers("/api/posts/**").permitAll()     // 👈 게시글 관련 API 전면 허용!
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll() // 스웨거 허용
                        .requestMatchers("/api/likes/**").authenticated() // 좋아요만 로그인 필요
                        .anyRequest().permitAll()                         // 나머지도 일단 모두 허용
                )

                // 4. 로그인/로그아웃 설정 (테스트를 위해 기본 폼 로그인 유지 혹은 필요시 주석처리)
                .formLogin(form -> form
                        .defaultSuccessUrl("/")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

