package com.pbs.chat.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class FacebookUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long fbUserId;

	@OneToOne(optional = false)
	@JoinColumn(name = "socialUserId", unique = true)
	private SocialUsersDetailsImpl socialUser;

	@Column(nullable = false, length = 16)
	private String gender;

	@Column(nullable = true)
	private Date birthDay;

	@OneToMany(mappedBy = "fbUser")
	private List<FbUserReferenceJoin> details;

	public long getFbUserId() {
		return fbUserId;
	}

	public void setFbUserId(long fbUserId) {
		this.fbUserId = fbUserId;
	}

	public SocialUsersDetailsImpl getSocialUser() {
		return socialUser;
	}

	public void setSocialUser(SocialUsersDetailsImpl socialUser) {
		this.socialUser = socialUser;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public List<FbUserReferenceJoin> getDetails() {
		return details;
	}

	public void setDetails(List<FbUserReferenceJoin> details) {
		this.details = details;
	}

}
