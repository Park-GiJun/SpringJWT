package com.security.springjwt.controller;

import com.security.springjwt.dto.CheckDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

	private final String MAIN_PAGE = "/MainPage/MainPage";

	@GetMapping("/main")
	public String mainPage() {
		return MAIN_PAGE;
	}

	@GetMapping("/main/info")
	public ModelAndView mainInfo() {
		System.out.println("Getting /main/info");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();

		CheckDTO checkDTO = new CheckDTO();
		checkDTO.setUsername(currentPrincipalName);
		checkDTO.setRole(role);

		System.out.println ("DTO : "  + checkDTO);

		ModelAndView modelAndView = new ModelAndView(MAIN_PAGE);
		modelAndView.addObject("checkDTO", checkDTO);

		return modelAndView;
	}
}
