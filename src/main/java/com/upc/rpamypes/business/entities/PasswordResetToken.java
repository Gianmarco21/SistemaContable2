package com.upc.rpamypes.business.entities;

import java.util.Date;

public class PasswordResetToken {

	private static final int EXPIRATION = 60 * 24;
	private int id;
	private String token;
	private String username;
	private Date expirydate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(Date expirydate) {
		this.expirydate = expirydate;
	}

	public PasswordResetToken(int id, String token, String username, Date expirydate) {
		this.id = id;
		this.token = token;
		this.username = username;
		this.expirydate = expirydate;
	}

	public PasswordResetToken() {
	}

}
