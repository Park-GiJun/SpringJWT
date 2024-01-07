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

		System.out.println ("username: " + username + ", password: " + password);

		// username과 password를 검증하기 위해서 Token에 넣어서 보내줌
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken (username, password, null);

		//token에 담은 검증을 위한 manager로 전달한후 검증 ( DB에서 회원 정보를 받아와서 검증 진행)
		return authManager.authenticate (authToken);
	}


	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
	@Override
	protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal ();

		String username = customUserDetails.getUsername ();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities ();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator ();
		GrantedAuthority auth = iterator.next ();

		String role = auth.getAuthority ();

		//username과 role을 이용해서 Token생성
		String token = jwtUtil.createJWT (username, role, 60*60*10L);

		//접두사를 붙이고 한칸을 띄워줘야함
		response.addHeader ("Authorization", "Bearer " + token);
	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication (HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
		response.setStatus (401);
	}

}
