package com.security.springjwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class UserPageController {

	private final String USER_PAGE = "/UserPage/UserPage";

	@PostMapping("/user")
	public String userPage () {
		log.info ("userPage Post");
		return "redirect:" + USER_PAGE;
	}
}
