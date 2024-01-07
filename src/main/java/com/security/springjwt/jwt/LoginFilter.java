package com.security.springjwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authManager;

	public LoginFilter (AuthenticationManager authManager) {
		this.authManager = authManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		//username, password를 가져옴
		String username = obtainUsername (request);
		String password = obtainPassword (request);

		System.out.println ("username: " + username + ", password: " + password);

		// username과 password를 검증하기 위해서 Token에 넣어서 보내줌
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (username, password, null);

		//token에 담은 검증을 위한 manager로 전달한후 검증 ( DB에서 회원 정보를 받아와서 검증 진행)
		return authManager.authenticate(authToken);
	}


	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

	}

}
