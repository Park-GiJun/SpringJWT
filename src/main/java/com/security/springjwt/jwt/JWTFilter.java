package com.security.springjwt.jwt;

import com.security.springjwt.dto.CustomUserDetails;
import com.security.springjwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

	public JWTFilter (JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (log.isInfoEnabled ()) {
			log.info ("doFilterInternal");
		}
		String token = null;

		Cookie[] cookies = request.getCookies ();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("jwtToken".equals (cookie.getName ())) {
					token = cookie.getValue ();
					break;
				}
			}
		}
		log.info ("doFilterInternal // token: " + token);

		if (token == null || jwtUtil.isExpired (token)) {
			filterChain.doFilter (request, response);
			return;
		}

		String username = jwtUtil.getUsername (token);
		String role = jwtUtil.getRole (token);

		log.info ("doFilterInternal // username: " + username + " role: " + role);

		UserEntity userEntity = new UserEntity ();
		userEntity.setUsername (username);
		userEntity.setPassword ("temp_password");  // 임시 비밀번호
		userEntity.setRole (role);

		CustomUserDetails customUserDetails = new CustomUserDetails (userEntity);
		Authentication authToken = new UsernamePasswordAuthenticationToken (customUserDetails, null, customUserDetails.getAuthorities ());

		SecurityContextHolder.getContext ().setAuthentication (authToken);
		filterChain.doFilter (request, response);
	}
}

