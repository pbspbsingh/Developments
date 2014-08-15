package com.pbs.chat.social;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.pbs.chat.component.dao.SocialUserDAO;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

public class SignInAdapterImpl implements SignInAdapter {

	private Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SocialUserDAO userInfoDao;
	
	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		try {
			final SocialUsersDetailsImpl user = userInfoDao.getByUserId(userId);
			if (user != null) {
				final Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(),
						user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Excetion while authenticating the user", e);
			throw new IllegalAddException("Failed to authenticate the user");
		}
		return null;
	}

}
