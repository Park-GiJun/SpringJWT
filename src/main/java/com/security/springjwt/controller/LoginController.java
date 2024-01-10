package com.security.springjwt.controller;

import com.security.springjwt.dto.CheckDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Context;
import java.util.Collection;
import java.util.Iterator;

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
