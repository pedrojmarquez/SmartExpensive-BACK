package com.smartexpensive.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // desactiva CSRF para pruebas locales
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/transcripcion/**").permitAll() // 👈 permitimos transcripcion sin login
                        .requestMatchers("/api/vision/**").permitAll() // 👈 permitimos visión sin login
                        .requestMatchers("/api/**").authenticated()  // proteger rutas /api/**
                        .anyRequest().permitAll()                     // el resto libre
                )
                // Spring Boot Security config
                .oauth2Login()
                    .defaultSuccessUrl("http://localhost:8100/home", true);



        return http.build();
    }
}