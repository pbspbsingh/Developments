package com.pbs.chat.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class UserPreferedRef {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long usrPrefRefId;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	private UserPref userPref;

	@OneToOne
	private FbReference fbReference;

	public long getUsrPrefRefId() {
		return usrPrefRefId;
	}

	public void setUsrPrefRefId(long usrPrefRefId) {
		this.usrPrefRefId = usrPrefRefId;
	}

	public UserPref getUserPref() {
		return userPref;
	}

	public void setUserPref(UserPref userPref) {
		this.userPref = userPref;
	}

	public FbReference getFbReference() {
		return fbReference;
	}

	public void setFbReference(FbReference fbReference) {
		this.fbReference = fbReference;
	}
	
}
