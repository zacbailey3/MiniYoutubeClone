package com.minitube.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity


public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                // REST APIs using JWT usually do not use browser CSRF tokens.
                .csrf(csrf -> csrf.disable())

                // Define which endpoints are public and which require login.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health", "/api/auth/register").permitAll()
                        .anyRequest().authenticated()
                )
                
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
