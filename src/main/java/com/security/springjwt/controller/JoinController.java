package com.security.springjwt.controller;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.service.JoinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

	private final JoinService joinService;

	public JoinController (JoinService joinService) {
		this.joinService = joinService;
	}

	@GetMapping("/join")
	public String join(){
        System.out.println ("Getting /join");

        return "ok";
    }

	@PostMapping("/join")
	public String joinProcess(JoinDTO joinDTO){
		System.out.println ("Posting /join");

		joinService.joinProcess (joinDTO);

		return "ok";
	}
}
