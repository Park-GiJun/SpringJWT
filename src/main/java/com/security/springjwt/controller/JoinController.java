package com.security.springjwt.controller;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JoinController {

	private final JoinService joinService;

	public JoinController (JoinService joinService) {
		this.joinService = joinService;
	}

	@GetMapping("/join")
	public String join () {
		log.info ("/join Get");
		return "ok";
	}

	@PostMapping("/join")
	public String joinProcess (JoinDTO joinDTO) {
		log.info ("/join Post");
		joinService.joinProcess (joinDTO);
		return "ok";
	}
}
