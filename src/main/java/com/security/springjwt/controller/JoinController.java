package com.security.springjwt.controller;

import com.security.springjwt.dto.JoinDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

	@PostMapping("/join")
	public String joinProcess(JoinDTO joinDTO){

		return "ok";
	}
}
