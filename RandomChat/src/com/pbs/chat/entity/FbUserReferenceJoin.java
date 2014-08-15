package com.pbs.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "fbUserId", "refId" }))
public class FbUserReferenceJoin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userRefJoinId;

	@ManyToOne
	@JoinColumn(name = "fbUserId", unique = false)
	private FacebookUser fbUser;

	@OneToOne
	@JoinColumn(name = "refId", unique = false)
	private FbReference reference;

	public FbUserReferenceJoin(FbReference reference) {
		this.reference = reference;
	}

	public long getUserRefJoinId() {
		return userRefJoinId;
	}

	public void setUserRefJoinId(long userRefJoinId) {
		this.userRefJoinId = userRefJoinId;
	}

	public FacebookUser getFbUser() {
		return fbUser;
	}

	public void setFbUser(FacebookUser fbUser) {
		this.fbUser = fbUser;
	}

	public FbReference getReference() {
		return reference;
	}

	public void setReference(FbReference reference) {
		this.reference = reference;
	}

}
