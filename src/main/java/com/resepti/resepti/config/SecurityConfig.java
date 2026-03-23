package com.resepti.resepti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig  {
 	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests( authorize -> authorize
				.requestMatchers("/","/login", "/css/**", "/h2-console/**").permitAll()
        .requestMatchers("/resepti/muokkaa/**", "/resepti/poista/**").hasRole("ADMIN")
				.anyRequest().authenticated()                 
			)                                       
		.formLogin( formlogin -> formlogin
      .loginPage("/login")                  
			.defaultSuccessUrl("/reseptit", true)
			.permitAll()                              
		)
		.logout( logout -> logout
			.permitAll()
		)

    .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
    .headers(headers -> headers.frameOptions(frame -> frame.disable()));
    
		return http.build();
	}
}