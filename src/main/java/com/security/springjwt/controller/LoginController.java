package com.security.springjwt.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
@Log4j2
public class LoginController {

	private final String LOGIN_PAGE = "/LoginPage/Login";

	@GetMapping("")
	public String LoginPage () {
		if (log.isInfoEnabled ()) {
			log.info ("Login Get");
		}
		return LOGIN_PAGE;
	}

}
