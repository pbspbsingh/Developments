package com.pbs.chat.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import com.pbs.chat.component.dao.SocialUserDAO;
import com.pbs.chat.entity.GrantedAuthorityImpl;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

public class ConnectionSignImpl implements ConnectionSignUp {

	@Autowired
	private SocialUserDAO userDAO;

	public static final String PASSWORD = "__password__";

	@Override
	public String execute(Connection<?> connection) {
		final UserProfile userProfile = connection.fetchUserProfile();
		if (userProfile.getEmail() == null || userProfile.getEmail().isEmpty())
			throw new IllegalArgumentException("Email Id cannot be null for a facebook user");

		final SocialUsersDetailsImpl user = new SocialUsersDetailsImpl();
		fillDataAndSave(user, userProfile);
		return user.getUserId();
	}

	private void fillDataAndSave(SocialUsersDetailsImpl user, UserProfile userProfile) {
		user.setUserId(userProfile.getEmail());
		user.setUsername(userProfile.getUsername() != null ? userProfile.getUsername() : userProfile.getFirstName() + " "
				+ userProfile.getLastName());
		user.setPassword(PASSWORD);
		final String authorityName = "ROLE_SOCIAL";
		GrantedAuthorityImpl authority = userDAO.getAuthority(authorityName);
		user.setAuthority(authority);
		userDAO.save(user);
	}

}
