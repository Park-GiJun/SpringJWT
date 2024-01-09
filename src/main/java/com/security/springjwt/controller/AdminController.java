package com.security.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

	private final String ADMIN_PAGE = "/AdminPage/AdminPage";

	@GetMapping("/admin")
	public String adminP(){
		System.out.println ("Getting /admin");
		return ADMIN_PAGE;
	}
}
