package com.pbs.chat.component.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pbs.chat.component.service.PreferenceService;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

@Controller
@RequestMapping(value = "mobile")
public class ChatController {
	
	@Autowired
	private PreferenceService prefService;

	@RequestMapping(value = "main.html", method = RequestMethod.GET)
	public String chatRoom(final Model model) {
		final SocialUsersDetailsImpl user = (SocialUsersDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> categoryList = prefService.getCategoryNames(user.getSocialUserId());
		model.addAttribute("categories", categoryList);
		model.addAttribute("name", user.getUsername());
		return "mobile/main";
	}

	@RequestMapping(value = "chat.html", method = RequestMethod.GET)
	public String startChat() {
		return "mobile/chat";
	}
}
