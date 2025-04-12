package com.enset.sdia.jeespringmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("user").build(),
                User.withUsername("admin1").password(passwordEncoder.encode("1234")).roles("user","admin").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/user/**").hasRole("user")
                        .requestMatchers("/webjars/**","/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/notAuthorized"))
                .formLogin(fl -> fl.loginPage("/login").permitAll())
                .build();
    }
}
