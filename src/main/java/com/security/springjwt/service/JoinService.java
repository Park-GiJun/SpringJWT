package com.security.springjwt.service;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.entity.UserEntity;
import com.security.springjwt.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	public JoinService (UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	public void joinProcess(JoinDTO joinDTO){
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();

		Boolean isExist = userRepository.existsByUsername(username);

		if(isExist) {
			return;
		}

		UserEntity data = new UserEntity();

		data.setUsername(username);
		data.setPassword(bcryptPasswordEncoder.encode (password));
		data.setRole ("ROLE_ADMIN");

		userRepository.save (data);
	}
}
