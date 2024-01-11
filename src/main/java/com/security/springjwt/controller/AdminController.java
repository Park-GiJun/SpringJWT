package com.security.springjwt.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Log4j2
public class AdminController {

	private final String ADMIN_PAGE = "/AdminPage/AdminPage";

	@GetMapping("")
	public String adminP () {
		if (log.isInfoEnabled ()) {
			log.info ("/admin");
		}
		return ADMIN_PAGE;
	}
}
