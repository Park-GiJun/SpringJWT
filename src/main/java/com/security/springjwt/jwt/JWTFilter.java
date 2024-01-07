package com.security.springjwt.jwt;

import com.security.springjwt.dto.CustomUserDetails;
import com.security.springjwt.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

	public JWTFilter (JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String authoerization = request.getHeader ("Authorization");

		//Authoerization 검증
		if (authoerization == null || !authoerization.startsWith ("Bearer ")) {
			filterChain.doFilter (request, response);
			return;
		}

		//순수 토큰만 획득
		String token = authoerization.split (" ")[1];

		//토큰 소멸시간 검증
		if (jwtUtil.isExpired (token)) {
			filterChain.doFilter (request, response);
			return;
		}

		//토큰에서 username과 role 획득
		String username = jwtUtil.getUsername (token);
		String role = jwtUtil.getRole (token);

		//userEntity를 생성하여 값 set
		UserEntity userEntity = new UserEntity ();
		userEntity.setUsername (username);
		//임시비밀번호
		userEntity.setPassword ("temppassword");
		userEntity.setRole (role);

		CustomUserDetails customUserDetails = new CustomUserDetails (userEntity);
		Authentication authToken = new UsernamePasswordAuthenticationToken (customUserDetails, null, customUserDetails.getAuthorities ());


		//세션에 사용자 등록
		SecurityContextHolder.getContext ().setAuthentication (authToken);

		filterChain.doFilter (request, response);
	}
}
