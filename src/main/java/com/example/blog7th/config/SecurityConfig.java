package com.example.blog7th.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //우선 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/likes/**").authenticated() // 좋아요 API는 인증
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.defaultSuccessUrl("/")) // 로그인 성공 시 이동할 곳
                .logout(logout -> logout.logoutSuccessUrl("/")); // 로그아웃 설정 나중에 필요할 때 추가

        return http.build();
    }
}
