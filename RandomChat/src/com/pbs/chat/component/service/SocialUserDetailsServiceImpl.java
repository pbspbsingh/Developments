package com.pbs.chat.component.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.pbs.chat.component.dao.SocialUserDAO;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

@Service
public class SocialUserDetailsServiceImpl implements SocialUserDetailsService {

	@Autowired
	SocialUserDAO socialUserDao;
	
	@Override
	public SocialUsersDetailsImpl loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		return socialUserDao.getByUserId(userId);
	}

}
