package com.smartexpensive.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/gastos/**").permitAll()
//                        .requestMatchers("/api/transcripcion/**").permitAll()
//                        .requestMatchers("/api/vision/**").permitAll()
                        .requestMatchers("/api/usuario").permitAll()
                        .requestMatchers("/login/success").permitAll() // 👈 permitimos el callback del login
                        .requestMatchers("/api/**").permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .successHandler((request, response, authentication) -> {
                            // 👇 redirige a nuestro controlador que genera el JWT
                            response.sendRedirect("/login/success");
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8100"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // 👈 acepta todos los encabezados
        config.setExposedHeaders(List.of("Authorization", "Content-Type")); // 👈 expone headers útiles
        config.setAllowCredentials(true); // 👈 permite cookies / credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
