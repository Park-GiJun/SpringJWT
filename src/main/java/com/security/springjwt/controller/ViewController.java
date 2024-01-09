package com.security.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	private final String MAIN_PAGE = "/MainPage/MainPage";
	private final String LOGIN_PAGE = "/LoginPage/Login";
	private final String ADMIN_PAGE = "/AdminPage/AdminPage";
	private final String USER_PAGE = "/UserPage/UserPage";


	@GetMapping("/view/login")
	public String mainP () {
		System.out.println ("Getting /login");
		return LOGIN_PAGE;
	}


	@GetMapping("/view/main")
	public String mainPage () {
		System.out.println ("Getting /main");
		return MAIN_PAGE;
	}

	@GetMapping("/view/join")
	public String join () {
		System.out.println ("Geting /join");

		return "/LoginPage/JoinPage";
	}


	@GetMapping("/view/admin")
	public String adminP () {
		System.out.println ("Posting /admin");
		return "redirect:" + ADMIN_PAGE;
	}

	@GetMapping("/view/user")
	public String userPage () {
		System.out.println ("Posting /user");
		return "redirect:" + USER_PAGE;
	}

}
