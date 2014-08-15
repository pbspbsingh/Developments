package com.pbs.chat.component.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.EducationEntry;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.WorkEntry;
import org.springframework.stereotype.Service;

import com.pbs.chat.component.dao.FbUserDAO;
import com.pbs.chat.entity.FacebookUser;
import com.pbs.chat.entity.FbUserReferenceJoin;
import com.pbs.chat.entity.SocialUsersDetailsImpl;

@Service
public class FacebookUserService {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private FbUserDAO fbuserDAO;

	public void createUserDetails(FacebookProfile fbUserProfile, SocialUsersDetailsImpl socialUserDetails) {
		final FacebookUser user = new FacebookUser();
		updateFBUser(fbUserProfile, socialUserDetails, user);
	}

	private void updateFBUser(FacebookProfile fbUserProfile, SocialUsersDetailsImpl socialUserDetails, final FacebookUser user) {
		if (fbUserProfile.getBirthday() != null && fbUserProfile.getLocale() != null)
			try {
				Date dob = new SimpleDateFormat("dd/MM/yyyy", fbUserProfile.getLocale()).parse(fbUserProfile.getBirthday());
				logger.debug("Got date of birth: " + dob);
				user.setBirthDay(dob);
			} catch (ParseException e) {
				logger.warn("Parsing of DOB failed.", e);
			}
		else
			logger.warn("Date of Birth is null for user: " + fbUserProfile.getId() + " : " + fbUserProfile.getUsername());
		if (user.getDetails() == null) {
			logger.debug("Setting an empty array list to user details");
			user.setDetails(new ArrayList<FbUserReferenceJoin>());
		}

		if (fbUserProfile.getLocation() != null && !isAlreadyAdded(user.getDetails(), fbUserProfile.getLocation()))
			user.getDetails().add(new FbUserReferenceJoin(fbuserDAO.getReference(fbUserProfile.getLocation(), Category.Location)));

		if (fbUserProfile.getHometown() != null && !isAlreadyAdded(user.getDetails(), fbUserProfile.getHometown()))
			user.getDetails().add(new FbUserReferenceJoin(fbuserDAO.getReference(fbUserProfile.getHometown(), Category.Location)));

		if (fbUserProfile.getInspirationalPeople() != null)
			for (Reference ref : fbUserProfile.getInspirationalPeople())
				if (!isAlreadyAdded(user.getDetails(), ref))
					user.getDetails().add(new FbUserReferenceJoin(fbuserDAO.getReference(ref, Category.Inspiring_People)));

		if (fbUserProfile.getSports() != null)
			for (Reference ref : fbUserProfile.getSports())
				if (!isAlreadyAdded(user.getDetails(), ref))
					user.getDetails().add(new FbUserReferenceJoin(fbuserDAO.getReference(ref, Category.Sports)));

		if (fbUserProfile.getFavoriteTeams() != null)
			for (Reference ref : fbUserProfile.getFavoriteTeams())
				if (!isAlreadyAdded(user.getDetails(), ref))
					user.getDetails().add(new FbUserReferenceJoin(fbuserDAO.getReference(ref, Category.FavTeam)));

		if (fbUserProfile.getWork() != null)
			for (WorkEntry work : fbUserProfile.getWork())
				if (!isAlreadyAdded(user.getDetails(), work.getEmployer()))
					user.getDetails().add(
							new FbUserReferenceJoin(fbuserDAO.getReference(work.getEmployer(), Category.Work, work.getStartDate(),
									work.getEndDate())));

		if (fbUserProfile.getEducation() != null)
			for (EducationEntry edu : fbUserProfile.getEducation())
				if (!isAlreadyAdded(user.getDetails(), edu.getSchool()))
					user.getDetails().add(
							new FbUserReferenceJoin(fbuserDAO.getReference(edu.getSchool(), Category.Education, edu.getYear() != null ? edu
									.getYear().getName() : null, edu.getType())));
		
		user.setSocialUser(socialUserDetails);
		user.setGender(fbUserProfile.getGender() != null ? fbUserProfile.getGender().toUpperCase() : "MALE");
		socialUserDetails.setLastUpdated(new Date());

		logger.debug("User details has been filled, let's save it now");
		fbuserDAO.saveOrUpdate(user, socialUserDetails);
	}

	private boolean isAlreadyAdded(List<FbUserReferenceJoin> categories, Reference ref) {
		for (FbUserReferenceJoin cat : categories)
			if (ref.getId().equals(cat.getReference().getRefId()))
				return true;
		return false;
	}

	public void refreshUserDetails(FacebookProfile fbUserProfile, SocialUsersDetailsImpl user) {
		FacebookUser fbUser = fbuserDAO.getFBUserBySocialUserId(user.getSocialUserId());
		fbuserDAO.clearCategories(fbUser.getFbUserId());
		fbUser.setDetails(null);
		updateFBUser(fbUserProfile, user, fbUser);
	}

	public static enum Category {
		Location("Location"), Inspiring_People("Inspiring People"), Sports("Sports"), FavTeam("Favourite Team"), Work("Work"), 
		Education("Education");

		private final String value;

		private Category(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
