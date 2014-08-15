package com.pbs.chat.component.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pbs.chat.entity.GrantedAuthorityImpl;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

@Repository
public class SocialUserDAO extends AbstractDAO<SocialUsersDetailsImpl> {

	@Transactional(readOnly = true)
	public SocialUsersDetailsImpl getByUserId(String email) {
		logger.debug("Got request to fetch socialuser by email: " + email);
		List<?> list = getCurrentSession()
						.createQuery("FROM SocialUsersDetailsImpl su where su.userId=:userId")
						.setString("userId", email)
						.list();
		logger.debug("Fetched list: " + list);
		return list.size() > 0 ? (SocialUsersDetailsImpl) list.get(0) : null;
	}

	@Transactional
	public GrantedAuthorityImpl getAuthority(String authority) {
		logger.debug("Quering GrantedAuthority: " + authority);
		final List<?> list = getCurrentSession()
								.createQuery("FROM GrantedAuthorityImpl ga where ga.authority=:authority")
								.setString("authority", authority)
								.list();
		logger.debug("Fetched list: " + list);
		if (list.isEmpty()) {
			logger.debug("There is no authority found in db with name: " + authority);
			GrantedAuthorityImpl auth = new GrantedAuthorityImpl();
			auth.setAuthority(authority);
			getCurrentSession().saveOrUpdate(auth);
			return auth;
		} else
			return (GrantedAuthorityImpl) list.get(0);
	}
	
}
