package com.pbs.chat.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UserPref {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long prefId;

	@OneToOne(optional = false)
	private FacebookUser user;

	@Column(nullable = false)
	private boolean likesMale = true;

	@Column(nullable = false)
	private boolean likesFemale = true;

	@Column(nullable = false)
	private int ageBelow = -10;

	@Column(nullable = false)
	private int ageAbove = +10;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userPref")
	private List<UserPreferedRef> preferences;
	
	@Column(nullable = true)
	private String nickName;
	
	@Column(nullable = false, length = 16)
	private String onlineStatus = "OFFLINE";

	public long getPrefId() {
		return prefId;
	}

	public void setPrefId(long prefId) {
		this.prefId = prefId;
	}

	public FacebookUser getUser() {
		return user;
	}

	public void setUser(FacebookUser user) {
		this.user = user;
	}

	public boolean isLikesMale() {
		return likesMale;
	}

	public void setLikesMale(boolean likesMale) {
		this.likesMale = likesMale;
	}

	public boolean isLikesFemale() {
		return likesFemale;
	}

	public void setLikesFemale(boolean likesFemale) {
		this.likesFemale = likesFemale;
	}

	public int getAgeBelow() {
		return ageBelow;
	}

	public void setAgeBelow(int ageBelow) {
		this.ageBelow = ageBelow;
	}

	public int getAgeAbove() {
		return ageAbove;
	}

	public void setAgeAbove(int ageAbove) {
		this.ageAbove = ageAbove;
	}

	public List<UserPreferedRef> getPreferences() {
		return preferences;
	}

	public void setPreferences(List<UserPreferedRef> preferences) {
		this.preferences = preferences;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	
}
