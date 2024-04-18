package com.smsytem.students.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class SpringSecurityConfig {
    // private UserDetailsService userDetailsService;
    // private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> {
                    // >>>>>>>>Role base Security.....>>>>>>>
                    // authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                    // authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN",
                    // "USER");
                    // authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("ADMIN",
                    // "USER");
                    
                    
                    // authorize.requestMatchers(HttpMethod.POST, "/api/todos/**").hasRole("ADMIN");
                    // authorize.requestMatchers("/api/auth/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic();
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    // PasswordEncoder encoder =
    // PasswordEncoderFactories.createDelegatingPasswordEncoder();
    // UserDetails jhon =
    // User.builder().username("jhon").password(encoder.encode("1234")).roles("USER").build();
    // UserDetails admin =
    // User.builder().username("admin").password(encoder.encode("password")).roles("ADMIN")
    // .build();
    // return new InMemoryUserDetailsManager(jhon, admin);
    // }
}
