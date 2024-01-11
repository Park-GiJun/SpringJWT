package com.security.springjwt.config;

import com.security.springjwt.handler.CustomAccessDeniedHandler;
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

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	public SecurityConfig (AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, CustomAccessDeniedHandler customAccessDeniedHandler) {

		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
		this.customAccessDeniedHandler = customAccessDeniedHandler;
	}

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

		http
				.csrf (AbstractHttpConfigurer::disable);

		http
				.formLogin (form -> form
						.loginPage ("/login")
						.usernameParameter ("username")
						.passwordParameter ("password")
						.permitAll ()
				);

		http
				.httpBasic (AbstractHttpConfigurer::disable);

		http
				.authorizeHttpRequests ((auth) -> auth
						.requestMatchers ( "/", "/login/**","/favicon.ico","/register/**").permitAll ()
						.requestMatchers ("/main").hasAnyRole ("USER", "ADMIN")
						.requestMatchers ("/user/**").hasRole ("USER")
						.requestMatchers ("/admin/**").hasRole ("ADMIN")
						.anyRequest ().authenticated ());

		http
				.logout (logout -> logout
						.deleteCookies ("jwtToken"));

		http
				.addFilterBefore (new JWTFilter (jwtUtil), LoginFilter.class);

		http
				.addFilterAt (new LoginFilter (authenticationManager (authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

		http
				.exceptionHandling (auth -> auth
						.accessDeniedHandler (customAccessDeniedHandler));

		http
				.sessionManagement ((auth) -> auth
						.sessionCreationPolicy (SessionCreationPolicy.STATELESS));
		return http.build ();
	}
}
