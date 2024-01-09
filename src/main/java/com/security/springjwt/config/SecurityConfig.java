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

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;

	public SecurityConfig (AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
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
						.loginProcessingUrl ("/login")
						.permitAll ()
				);

		http
				.httpBasic (AbstractHttpConfigurer::disable);

		http
				.authorizeHttpRequests ((auth) -> auth
						.requestMatchers ("/LoginPage/*", "/", "/join", "/login").permitAll ()
						.requestMatchers ("/main").hasAnyRole ("USER", "ADMIN")
						.requestMatchers ("/user").hasRole ("USER")
						.requestMatchers ("/admin").hasRole ("ADMIN")
						.anyRequest ().authenticated ());

		http
				.logout (logout -> logout
						.deleteCookies ("jwtToken"));

		System.out.println ("인가작업 통과");
		http
				.addFilterBefore (new JWTFilter (jwtUtil), LoginFilter.class);

		http
				.addFilterAt (new LoginFilter (authenticationManager (authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

		http
				.sessionManagement ((auth) -> auth
						.sessionCreationPolicy (SessionCreationPolicy.STATELESS));
		return http.build ();
	}

}
