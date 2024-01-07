package com.security.springjwt.controller;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

	private final JoinService joinService;

	public JoinController (JoinService joinService) {
		this.joinService = joinService;
	}

	@PostMapping("/join")
	public String joinProcess(JoinDTO joinDTO){

		joinService.joinProcess (joinDTO);

		return "ok";
	}
}
