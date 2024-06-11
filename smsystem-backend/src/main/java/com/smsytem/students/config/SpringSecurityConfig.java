package com.smsytem.students.config;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smsytem.students.security.JwtAuthenticationEntryPoint;
import com.smsytem.students.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.disable())
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorize -> {
                                        authorize.requestMatchers(HttpMethod.POST, "/api/teachers/**",
                                                        "/api/classes/**",
                                                        "/api/subjects/**")
                                                        .hasAnyRole("TEACHER", "ADMIN");
                                        // authorize
                                        // .requestMatchers(HttpMethod.GET, "/api/students/**")
                                        // .hasAnyRole("TEACHER", "ADMIN", "STUDENT");
                                        authorize.requestMatchers(HttpMethod.GET, "/api/students/**", "/api/classes/**",
                                                        "/api/subjects/**").permitAll();
                                        // Allow public access to authentication endpoints and error page
                                        authorize
                                                        .requestMatchers("/api/auth/**", "/error")
                                                        .permitAll();
                                        // Require authentication for any other requests
                                        authorize.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                                        authorize
                                                        .anyRequest()
                                                        .authenticated();
                                })
                                .httpBasic(withDefaults());

                http.exceptionHandling(exception -> exception
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint));

                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }
}
