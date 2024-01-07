package com.security.springjwt.service;

import com.security.springjwt.dto.JoinDTO;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

	public void joinProcess(JoinDTO joinDTO){
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();

	}
}
