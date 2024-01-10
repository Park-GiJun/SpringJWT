package com.security.springjwt.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class AdminController {

	private final String ADMIN_PAGE = "/AdminPage/AdminPage";

	@GetMapping("/admin")
	public String adminP(){
		log.info("/admin");
		return ADMIN_PAGE;
	}
}
