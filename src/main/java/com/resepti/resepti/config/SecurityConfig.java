package com.resepti.resepti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/css/**", "/h2-console/**").permitAll()
            .requestMatchers("/resepti/muokkaa/**", "/resepti/poista/**").hasRole("ADMIN")
            .requestMatchers("/tagit", "/ainesosat").hasRole("ADMIN")
            .requestMatchers("/api/**").authenticated()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/reseptit", true)
            .permitAll())
        .httpBasic(withDefaults())
        .logout(logout -> logout.permitAll())
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/api/**", "/h2-console/**"))
        .headers(headers -> headers
            .frameOptions(frame -> frame.disable()));

    return http.build();
  }
}