package com.security.springjwt.controller;

import com.security.springjwt.dto.CheckDTO;
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
public class LoginController {

	private final String LOGIN_PAGE = "/LoginPage/Login";

	@GetMapping("/login")
	@ResponseBody
	public ModelAndView mainP (Model model) {
		String username = SecurityContextHolder.getContext ().getAuthentication ().getName ();
		Authentication authentication = SecurityContextHolder.getContext ().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority authority = iter.next();
		String role = authority.getAuthority ();

		System.out.println ("Getting /login");
		CheckDTO checkDTO = new CheckDTO ();
		checkDTO.setUsername (username);
		checkDTO.setRole (role);
		System.out.println ("MainP / username: " + username + ", role: " + role);
		ModelAndView mav = new ModelAndView (LOGIN_PAGE);
		mav.addObject ("checkDTO", checkDTO);
		return mav;
	}

}
