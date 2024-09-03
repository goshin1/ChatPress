package com.example.chatpress.config;

import com.example.chatpress.jwt.CustomLogoutFilter;
import com.example.chatpress.jwt.JWTFilter;
import com.example.chatpress.jwt.JWTUtil;
import com.example.chatpress.jwt.LoginFilter;
import com.example.chatpress.repository.RefreshRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final RefreshRepository refreshRepository;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    public SecurityConfig(RefreshRepository refreshRepository, AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil){
        this.refreshRepository = refreshRepository;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf((auth) -> auth.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/login", "/", "/join","/reissue").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/document", "/chat", "/invite", "/message", "/room", "/user").authenticated()
                .anyRequest().permitAll()
        );

        // 필터 3개
        // jwt 필터 등록
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);


        // 필터 추가 LoginFilter()는 인자를 받음(AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository), UsernamePasswordAuthenticationFilter.class);

        // 로그아웃 필터
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);


        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
        
    }


}
