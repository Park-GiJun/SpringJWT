package com.security.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserPageController {

	private final String USER_PAGE = "/UserPage/UserPage";

	@PostMapping("/user")
	public String userPage () {
		System.out.println ("Posting /user");
		return "redirect:" + USER_PAGE;
	}
}
