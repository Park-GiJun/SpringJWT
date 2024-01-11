package com.security.springjwt.controller;

import com.security.springjwt.dto.JoinDTO;
import com.security.springjwt.service.JoinService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/register")
@Log4j2
public class JoinController {

	private final JoinService joinService;

	private final String LOGIN_PAGE = "LoginPage/Login";
	private final String REGISTER_PAGE = "LoginPage/RegisterPage";

	public JoinController (JoinService joinService) {
		this.joinService = joinService;
	}

	@GetMapping("")
	public String join (Model model) {
		if (log.isInfoEnabled ()) {
			log.info ("/register Get");
		}
		model.addAttribute ("joinDTO", new JoinDTO ());
		return REGISTER_PAGE;
	}

	@PostMapping("/register")
	public String joinProcess (JoinDTO joinDTO) {
		log.info ("register post");
		joinService.joinProcess (joinDTO);
		return LOGIN_PAGE;
	}
}
