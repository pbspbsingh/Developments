package com.pbs.chat.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Dummy entity to help JDBCUserConnectionReposity to create it's table
 * Do not edit this class
 * @author singhpra
 *
 */

@Entity
@Table(uniqueConstraints={
	@UniqueConstraint(columnNames={"userId", "providerId", "rank"})
})
class UserConnection {

	@Id
	private UserConnectionId id;

	@Column(nullable = false)
	private int rank;

	@Column(length = 255)
	private String displayName;

	@Column(length = 512)
	private String profileUrl;

	@Column(length = 512)
	private String imageUrl;

	@Column(nullable = false, length = 255)
	private String accessToken;

	@Column(length = 255)
	private String secret;

	@Column(length = 255)
	private String refreshToken;

	@Column
	private long expireTime;

	public UserConnectionId getId() {
		return id;
	}

	public void setId(UserConnectionId id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	@Embeddable
	public static class UserConnectionId implements Serializable {

		private static final long serialVersionUID = -8643721949544071211L;

		@Column(nullable = false, length = 255)
		private String userId;

		@Column(nullable = false, length = 255)
		private String providerId;

		@Column(nullable = true, length = 255)
		private String providerUserId;

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getProviderId() {
			return providerId;
		}

		public void setProviderId(String providerId) {
			this.providerId = providerId;
		}

		public String getProviderUserId() {
			return providerUserId;
		}

		public void setProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
		}

	}

}
