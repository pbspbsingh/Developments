package com.pbs.chat.component.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbs.chat.component.dao.FbUserDAO;
import com.pbs.chat.component.dao.PreferenceDAO;
import com.pbs.chat.entity.FacebookUser;
import com.pbs.chat.entity.FbReference;
import com.pbs.chat.entity.NickName;
import com.pbs.chat.entity.SocialUsersDetailsImpl;
import com.pbs.chat.entity.UserPref;
import com.pbs.chat.entity.UserPreferedRef;

@Service
public class PreferenceService {

	@Autowired
	private PreferenceDAO prefDao;

	@Autowired
	private FbUserDAO facebookUserDAO;

	public UserPref loadPrefernce(SocialUsersDetailsImpl user) {
		final FacebookUser fbUser = facebookUserDAO.getFBUserBySocialUserId(user.getSocialUserId());

		UserPref pref = prefDao.getPreferenceByFbUsrId(fbUser.getFbUserId());
		if (pref == null)
			pref = new UserPref();
		else
			prefDao.clearPreferences(pref.getPrefId());
		
		final List<NickName> nickNames = prefDao.loadNickNames();
		final NickName nickName = nickNames.get(new Random().nextInt(nickNames.size()));
		pref.setNickName(nickName.getNickName());
		pref.setUser(fbUser);
		prefDao.saveOrUpdate(pref);
		return pref;
	}

	public UserPref loadPrefernce(long prefId) {
		return prefDao.loadById(UserPref.class, prefId);
	}

	public void replicateAndSave(UserPref pref) {
		pref.setPreferences(new ArrayList<UserPreferedRef>());
		final List<FbReference> cats = facebookUserDAO.getAllCategories(pref.getUser().getFbUserId());
		for (FbReference fbref : cats) {
			UserPreferedRef ref = new UserPreferedRef();
			ref.setUserPref(pref);
			ref.setFbReference(fbref);
			
			pref.getPreferences().add(ref);
		}
		prefDao.saveOrUpdate(pref);
	}

	public List<String> getCategoryNames(long socialUserId) {
		final List<String> allCategories = prefDao.loadCategoriesNames(socialUserId);
		final Set<String> uniqueCat = new TreeSet<String>(allCategories);
		return new ArrayList<String>(uniqueCat);
	}

}
