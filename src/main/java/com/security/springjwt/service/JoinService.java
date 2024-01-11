package com.security.springjwt.service;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.entity.UserEntity;
import com.security.springjwt.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JoinService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;

	public JoinService (UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
	}

	public void joinProcess(JoinDTO joinDTO){
		log.info ("joinProcess");
		String username = joinDTO .getUsername();
		String password = joinDTO.getPassword();

		Boolean isExist = userRepository.existsByUsername(username);

		if(isExist) {
			return;
		}

		UserEntity data = new UserEntity();

		data.setUsername(username);
		data.setPassword(bcryptPasswordEncoder.encode (password));
		data.setRole ("ROLE_USER");

		userRepository.save (data);
	}
}
