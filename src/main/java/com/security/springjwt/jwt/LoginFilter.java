package com.security.springjwt.jwt;

import com.security.springjwt.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authManager;
	private final JWTUtil jwtUtil;

	public LoginFilter (AuthenticationManager authManager, JWTUtil jwtUtil) {
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		//username, password를 가져옴
		String username = obtainUsername (request);
		String password = obtainPassword (request);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (username, password, null);

		return authManager.authenticate (authToken);
	}

	@Override
	protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal ();

		String username = customUserDetails.getUsername ();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities ();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator ();
		GrantedAuthority auth = iterator.next ();

		String role = auth.getAuthority ();

		String token = jwtUtil.createJWT (username, role, 60*60*10L);

		System.out.println ("로그인 성공 : " + token);

		response.addHeader ("Authorization", "Bearer " + token);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"token\":\"" + token + "\"}");
	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication (HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		response.setStatus (401);
	}

}
