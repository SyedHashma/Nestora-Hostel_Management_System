package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ✅ PUBLIC
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/", "/login.html", "/signup.html",
                                 "/admin-dashboard.html", "/user-dashboard.html",
                                 "/css/**", "/js/**").permitAll()

                // 🔥 ADD THESE
                .requestMatchers("/rooms/**").permitAll()
                .requestMatchers("/residents/**").permitAll()

                // 🔐 REST
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}