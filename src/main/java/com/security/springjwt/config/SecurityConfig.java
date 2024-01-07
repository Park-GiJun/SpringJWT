package com.security.springjwt.config;

import com.security.springjwt.jwt.JWTFilter;
import com.security.springjwt.jwt.JWTUtil;
import com.security.springjwt.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
	}


	//AuthenticationManager Bean 등록
	@Bean
	public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {

		return authenticationConfiguration.getAuthenticationManager ();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder ();
	}

	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

		//csrf disabled
		http
				.csrf (AbstractHttpConfigurer::disable);

		//form login disabled
		http
				.formLogin (AbstractHttpConfigurer::disable);

		//http basic disabled
		http
				.httpBasic (AbstractHttpConfigurer::disable);

		// 경로별 인가작업
		http
				.authorizeHttpRequests ((auth) -> auth
						.requestMatchers ("/login", "/", "/join").permitAll ()
						.requestMatchers ("/admin").hasRole ("ADMIN")
						.anyRequest ().authenticated ());

		http
				.addFilterBefore (new JWTFilter (jwtUtil), LoginFilter.class);
		//로그인 필터 추가
		http
				.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

		// 세션 설정
		http
				.sessionManagement ((auth) -> auth
						.sessionCreationPolicy (SessionCreationPolicy.STATELESS));
		return http.build ();
	}

}
