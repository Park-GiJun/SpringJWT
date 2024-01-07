package com.security.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder ();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//csrf disabled
		http
				.csrf ((auth) -> auth.disable ());

		//form login disabled
		http
				.formLogin ((auth) -> auth.disable ());

		//http basic disabled
		http
				.httpBasic ((auth) -> auth.disable ());

		// 경로별 인가작업
		http
				.authorizeHttpRequests ((auth) -> auth
						.requestMatchers ("/login", "/", "/join").permitAll ()
						.requestMatchers ("/admin").hasRole ("ADMIN")
						.anyRequest ().authenticated ());

		// 세션 설정
		http
				.sessionManagement ((auth) -> auth
                        .sessionCreationPolicy (SessionCreationPolicy.STATELESS));
		return http.build ();
	}

}
