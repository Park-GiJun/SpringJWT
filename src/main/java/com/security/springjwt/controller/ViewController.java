package com.security.springjwt.controller;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ViewController {

	private final String ACCESS_DENIED_PAGE = "/ErrorPage/AccessDeniedPage";

	@GetMapping("/accessDenied")
	public String accessDenied() {
		if (log.isInfoEnabled ()) {
			log.info ("Access denied");
		}
        return ACCESS_DENIED_PAGE;
    }

}
