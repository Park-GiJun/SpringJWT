package com.security.springjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

	private final String LOGIN_PAGE = "/LoginPage/Login";

	@GetMapping("/login")
	public String LoginPage () {
		log.info ("Login Get");
		return LOGIN_PAGE;
	}

}
