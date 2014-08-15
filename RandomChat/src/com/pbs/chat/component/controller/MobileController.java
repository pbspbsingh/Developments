package com.pbs.chat.component.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpServerErrorException;

import com.pbs.chat.component.service.FacebookUserService;
import com.pbs.chat.component.service.PreferenceService;
import com.pbs.chat.entity.SocialUsersDetailsImpl;
import com.pbs.chat.entity.UserPref;

@Controller
@RequestMapping(value = "mobile")
public class MobileController {

	@Autowired
	private UsersConnectionRepository usersConn;

	@Autowired
	private FacebookUserService fbUserService;
	
	@Autowired
	private PreferenceService prefService;

	@RequestMapping(value = { "/", "index.html" })
	public String firstPage(Principal principle, final Model model) {
		model.addAttribute("name", principle.getName());
		return "mobile/firstPage";
	}

	@RequestMapping(value = "refresh.html", method = RequestMethod.GET)
	public void refresh(ServletResponse response) throws IOException {
		final SocialUsersDetailsImpl user = (SocialUsersDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final ConnectionRepository connectionRepository = usersConn.createConnectionRepository(user.getUserId());
		if (connectionRepository == null)
			throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "You don't have facbook account");

		final Connection<Facebook> fbConnection = connectionRepository.getPrimaryConnection(Facebook.class);
		final Facebook fbAPI = fbConnection.getApi();
		if (fbAPI == null)
			throw new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE, "You don't have facbook account");

		if (user.getLastUpdated() == null) {
			if (fbAPI.userOperations() != null && fbAPI.userOperations().getUserProfile() != null) {
				final FacebookProfile fbUserProfile = fbAPI.userOperations().getUserProfile();
				fbUserService.createUserDetails(fbUserProfile, user);
			}

		} else if ((System.currentTimeMillis() - user.getLastUpdated().getTime()) > 7L * 24 * 60 * 1000) {
			if (fbAPI.userOperations() != null && fbAPI.userOperations().getUserProfile() != null) {
				final FacebookProfile fbUserProfile = fbAPI.userOperations().getUserProfile();
				fbUserService.refreshUserDetails(fbUserProfile, user);
			}
		}
		response.setContentType("application/json");
		final JSONObject root = new JSONObject();
		root.put("status", "SUCCESS");
		response.getWriter().write(root.toString());
	}

	@RequestMapping(value = "preference.html", method = RequestMethod.GET)
	public String preference(final Model model) {
		final SocialUsersDetailsImpl user = (SocialUsersDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final UserPref pref = prefService.loadPrefernce(user);
		model.addAttribute(pref);
		return "mobile/preference";
	}
	
	@RequestMapping(value = "savePref.html", method = RequestMethod.POST)
	public void savePreference(ServletResponse response,
			@RequestParam(required = true, value = "nickName") String nickName,
			@RequestParam(required = true, value = "prefId") long prefId, 
			@RequestParam(required = false, value = "likesMale") String male,
			@RequestParam(required = false, value = "likesFemale") String female,
			@RequestParam(required = true, value = "ageRange-1a") int lowerLimit,
			@RequestParam(required = true, value = "ageRange-1b") int upperLimit) throws IOException {

		final UserPref pref = prefService.loadPrefernce(prefId);
		pref.setLikesMale(male != null);
		pref.setLikesFemale(female != null);
		
		pref.setAgeBelow(lowerLimit);
		pref.setAgeAbove(upperLimit);
		pref.setOnlineStatus("AWAY");
		prefService.replicateAndSave(pref);
		
		response.setContentType("application/json");
		final JSONObject root = new JSONObject();
		root.put("status", "SUCCESS");
		response.getWriter().write(root.toString());
	}
}
