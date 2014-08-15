package com.pbs.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "granted_authority", uniqueConstraints = { @UniqueConstraint(columnNames = "authority") })
public class GrantedAuthorityImpl implements GrantedAuthority {

	private static final long serialVersionUID = -5564879993842628188L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int authorityId;

	@Column(nullable = false, length = 20)
	private String authority;


	public int getAuthorityId() {
		return authorityId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + authorityId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrantedAuthorityImpl other = (GrantedAuthorityImpl) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (authorityId != other.authorityId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrantedAuthorityImpl [authorityId=" + authorityId + ", authority=" + authority + "]";
	}
	
}
