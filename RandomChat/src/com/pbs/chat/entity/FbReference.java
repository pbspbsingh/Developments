package com.pbs.chat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FbReference {

	@Id
	@Column(nullable = false, length = 256)
	private String refId;

	@Column(nullable = false, length = 256)
	private String category;

	@Column(nullable = true, length = 512)
	private String extra1;

	@Column(nullable = true, length = 512)
	private String extra2;

	@Column(nullable = true, length = 512)
	private String extra3;

	@Column(nullable = true, length = 512)
	private String extra4;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getExtra1() {
		return extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getExtra4() {
		return extra4;
	}

	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}

}
