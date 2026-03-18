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
				.requestMatchers("/","/login", "/css/**").permitAll()
				.anyRequest().authenticated()                 
			)                                       
		.formLogin( formlogin -> formlogin
      .loginPage("/login")                  
			.defaultSuccessUrl("/reseptit", true)
			.permitAll()                              
		)
		.logout( logout -> logout
			.permitAll()
		);
		return http.build();
	}
}