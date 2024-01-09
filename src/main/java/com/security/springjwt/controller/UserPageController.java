package com.security.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserPageController {
	@PostMapping("/user")
	public String userPage () {
		return "/UserPage/UserPage";
	}
}
