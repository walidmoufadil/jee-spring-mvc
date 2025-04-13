package com.enset.sdia.jeespringmvc.security;

import com.enset.sdia.jeespringmvc.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

   //@Bean
   InMemoryUserDetailsManager inMemoryUserDetailsManager(){
       return new InMemoryUserDetailsManager(
               User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("user").build(),
               User.withUsername("admin1").password(passwordEncoder.encode("1234")).roles("user","admin").build()
       );
   }

   //@Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
       return  new JdbcUserDetailsManager(dataSource);
   }

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("admin")
                        .requestMatchers("/user/**").hasRole("user")
                        .requestMatchers("/webjars/**","h2-console/**").permitAll()
                        .anyRequest().authenticated()
                );

                http
                        .exceptionHandling(ex -> ex.accessDeniedPage("/notAuthorized"));
                http
                        .formLogin(fl -> fl.loginPage("/login").defaultSuccessUrl("/").permitAll());
                http
                        .userDetailsService(userDetailsService);
                return http.build();
    }


}
