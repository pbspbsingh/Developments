package com.pbs.chat.component.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pbs.chat.entity.NickName;
import com.pbs.chat.entity.UserPref;

@Repository
public class PreferenceDAO extends AbstractDAO<UserPref> {

	@Transactional(readOnly = true)
	public UserPref getPreferenceByFbUsrId(long fbUserId) {
		logger.debug("Got a request to fetch UserPref by FbUserId: " + fbUserId);
		List<?> list = getCurrentSession()
						.createCriteria(UserPref.class)
						.add(Restrictions.eq("user.fbUserId", fbUserId))
						.list();
		return list.size() > 0 ? (UserPref) list.get(0) : null;
	}

	@Transactional
	public int clearPreferences(long prefId) {
		logger.debug("Clearing UserPreferedRef for prefId" + prefId);
		return getCurrentSession()
				.createQuery("delete from UserPreferedRef upr where upr.userPref.prefId=:prefId")
				.setLong("prefId", prefId)
				.executeUpdate();
	}

	/**
	 * One time helper method to insert dummy values in NickNames table.
	 */
	@Transactional
	public void insertNickNames() {
		logger.info("Clearning the entires of NickNames");
		getCurrentSession().createQuery("delete from NickName").executeUpdate();
		
		try(InputStream is = getClass().getResourceAsStream("/dota-heros.txt");
				BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
			logger.info("Inserting the data into nickname table.");
			String line = null;
			while((line=reader.readLine())!=null) {
				NickName name = new NickName(line.trim());
				getTemplate().save(name);
			}
		} catch (IOException e) {
			logger.error("Error while reading the dota-heros.txt");
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<NickName> loadNickNames() {
		final List<?> list = getCurrentSession().createCriteria(NickName.class).list();
		return (List<NickName>) list;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<String> loadCategoriesNames(long socialUserId) {
		return getCurrentSession()
				.createQuery("select upr.fbReference.category from UserPreferedRef upr where upr.userPref.user.socialUser.socialUserId=:socialUserId")
				.setLong("socialUserId", socialUserId)
				.list();
	}
}
