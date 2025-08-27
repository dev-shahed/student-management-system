package com.smsytem.students.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smsytem.students.security.CustomUserDetailsService;
import com.smsytem.students.security.JwtAuthenticationEntryPoint;
import com.smsytem.students.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;

    public SpringSecurityConfig(CustomUserDetailsService userDetailsService,
                               JwtAuthenticationEntryPoint authenticationEntryPoint,
                               JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            
            // Public endpoints
            .antMatchers("/api/auth/**").permitAll()
            
            // Swagger endpoints (Springfox 3.0)
            .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger-resources/**").permitAll()
            .antMatchers("/v2/api-docs", "/v3/api-docs/**", "/webjars/**").permitAll()
            .antMatchers("/configuration/ui", "/configuration/security").permitAll()
            
            // Admin-only endpoints
            .antMatchers(HttpMethod.POST, "/api/students").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/students/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/teachers").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/teachers/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/teachers/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/classes").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/classes/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/classes/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/subjects").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/subjects/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/subjects/**").hasRole("ADMIN")
            
            // Teacher and Admin can manage attendance, exams, and results
            .antMatchers(HttpMethod.POST, "/api/attendance", "/api/attendance/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.PUT, "/api/attendance/**", "/api/attendance/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.DELETE, "/api/attendance/**", "/api/attendance/**").hasAnyRole("ADMIN", "TEACHER")
            
            // Exam management - Admin and Teachers
            .antMatchers(HttpMethod.POST, "/api/exams").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.PUT, "/api/exams/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.DELETE, "/api/exams/**").hasAnyRole("ADMIN", "TEACHER")
            
            // Exam results - Admin and Teachers
            .antMatchers(HttpMethod.POST, "/api/exam-results").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.PUT, "/api/exam-results/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.DELETE, "/api/exam-results/**").hasAnyRole("ADMIN", "TEACHER")
            
            // Fee management - Admin and Accountant
            .antMatchers(HttpMethod.POST, "/api/fees").hasAnyRole("ADMIN", "ACCOUNTANT")
            .antMatchers(HttpMethod.PUT, "/api/fees/**").hasAnyRole("ADMIN", "ACCOUNTANT")
            .antMatchers(HttpMethod.DELETE, "/api/fees/**").hasAnyRole("ADMIN", "ACCOUNTANT")
            
            // Notifications - Admin and Teachers
            .antMatchers(HttpMethod.POST, "/api/notifications").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.PUT, "/api/notifications/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.DELETE, "/api/notifications/**").hasAnyRole("ADMIN", "TEACHER")
            
            // Timetable - Admin and Teachers
            .antMatchers(HttpMethod.POST, "/api/timetable").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.PUT, "/api/timetable/**").hasAnyRole("ADMIN", "TEACHER")
            .antMatchers(HttpMethod.DELETE, "/api/timetable/**").hasAnyRole("ADMIN", "TEACHER")
            
            // Parent management - Admin only
            .antMatchers(HttpMethod.POST, "/api/parent").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/api/parent/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/api/parent/**").hasRole("ADMIN")
            
            // All other requests need authentication
            .anyRequest().authenticated();
            
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}