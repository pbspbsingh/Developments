package com.pbs.chat.component.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pbs.chat.component.service.FacebookUserService.Category;
import com.pbs.chat.entity.FacebookUser;
import com.pbs.chat.entity.FbReference;
import com.pbs.chat.entity.FbUserReferenceJoin;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

@Repository
public class FbUserDAO extends AbstractDAO<FacebookUser> {

	@Transactional
	public FbReference getReference(Reference reference, Category category, String ext2, String ext3) {
		logger.debug("Got a request to fetch Reference: " + reference.getName() + " category: " + category + "Extra2: " + ext2 + "Extra3: "
				+ ext3);
		List<?> references = getCurrentSession()
				.createQuery("FROM FbReference fr where fr.refId=:refId")
				.setString("refId", reference.getId())
				.list();
		FbReference ref = references.size() > 0 ? (FbReference) references.get(0) : null;
		if (ref == null) {
			logger.debug("Couldn't fetch reference from db, creating it");
			ref = new FbReference();
			ref.setRefId(reference.getId());
			ref.setCategory(category.getValue());
			ref.setExtra1(reference.getName());
			ref.setExtra2(ext2);
			ref.setExtra3(ext3);
			getTemplate().save(ref);
		}
		return ref;
	}

	@Transactional
	public FbReference getReference(Reference ref, Category category) {
		return getReference(ref, category, null, null);
	}

	@Transactional
	public void saveOrUpdate(FacebookUser user, SocialUsersDetailsImpl socialUserDetails) {
		logger.debug("Saving/Updating FbUser " + user.getFbUserId() + "  and SocialUserDetailImpl:" + socialUserDetails.getSocialUserId());
		getTemplate().saveOrUpdate(socialUserDetails);
		getTemplate().saveOrUpdate(user);
		for(FbUserReferenceJoin join : user.getDetails()){
			join.setFbUser(user);
			getTemplate().saveOrUpdate(join);
		}
	}

	@Transactional(readOnly = true)
	public FacebookUser getFBUserBySocialUserId(long socialUserId) {
		List<?> list = getCurrentSession().createCriteria(FacebookUser.class).add(Restrictions.eq("socialUser.socialUserId", socialUserId))
				.list();
		logger.debug("Fetched list of facebookuser by socialUserId " + socialUserId + ", the list is: " + list);
		return list.size() > 0 ? (FacebookUser) list.get(0) : null;
	}

	@Transactional
	public int clearCategories(long fbUserId) {
		logger.debug("Deleting the categories entires for fbUserId: " + fbUserId);
		return getCurrentSession()
				.createQuery("delete from FbUserReferenceJoin frj where frj.fbUser.fbUserId=:fbUserId")
				.setLong("fbUserId", fbUserId)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<FbReference> getAllCategories(long fbUserId) {
		return getCurrentSession()
				.createQuery("select frj.reference from FbUserReferenceJoin frj where frj.fbUser.fbUserId=:userId")
				.setLong("userId", fbUserId)
				.list();

	}

}
