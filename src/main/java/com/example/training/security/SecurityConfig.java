package com.example.training.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("mohamed")
                .password("{noop}1234") // كلمة المرور بدون تشفير (noop = no operation)
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // تعطيل CSRF مؤقتًا للـ REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public").permitAll()   // API مفتوح للجميع
                        .requestMatchers("/api/user").hasRole("USER") // يجب أن يكون USER
                        .requestMatchers("/api/admin").hasRole("ADMIN") // يجب أن يكون ADMIN
                        .anyRequest().authenticated() // باقي الطلبات تحتاج تسجيل دخول
                )
                .formLogin(withDefaults()); // استخدام شاشة Login افتراضية

        return http.build();
    }
}
