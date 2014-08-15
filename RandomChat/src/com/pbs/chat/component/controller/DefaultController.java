package com.pbs.chat.component.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	@RequestMapping(value = { "/", "index.html" })
	public String welcome() {
		return "index";
	}

	@RequestMapping(value = "decider.html")
	public String decider() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			for (GrantedAuthority auth : authentication.getAuthorities())
				if (auth.getAuthority().equals("ROLE_SOCIAL"))
					return "redirect:mobile/index.html";
		}
		return "error";
	}
}
