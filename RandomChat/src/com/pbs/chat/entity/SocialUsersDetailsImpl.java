package com.pbs.chat.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.social.security.SocialUserDetails;

@Entity
@Table(name = "SocialUser", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId" }) })
public class SocialUsersDetailsImpl implements SocialUserDetails {

	private static final long serialVersionUID = 3700611723210799789L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long socialUserId;

	@Column(nullable = false, length = 255)
	private String userId;

	@Column(length = 255, nullable = false)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, referencedColumnName = "authorityId", name = "authorityId")
	private GrantedAuthorityImpl authority;

	@Column(nullable = false)
	private boolean accountNonExpired = true;

	@Column(nullable = false)
	private boolean accountNonLocked = true;

	@Column(nullable = false)
	private boolean credentialsNonExpired = true;

	@Column(nullable = false)
	private boolean enabled = true;

	private Date lastUpdated;
	
	@Column(nullable = false)
	private Date creationTime = new Date();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<GrantedAuthorityImpl> getAuthorities() {
		return Arrays.asList(authority);
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public long getSocialUserId() {
		return socialUserId;
	}

	public GrantedAuthorityImpl getAuthority() {
		return authority;
	}

	public void setAuthority(GrantedAuthorityImpl authority) {
		this.authority = authority;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
}
