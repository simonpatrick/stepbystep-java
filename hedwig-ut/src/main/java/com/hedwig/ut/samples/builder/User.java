package com.hedwig.ut.samples.builder;

import java.util.Date;

public class User {

	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String timezone;
	private Date registered;
	private UserState state;
	private Address address;

	public User(String firstName, String lastName, String password, String email, String timezone, Date registered, UserState state, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.timezone = timezone;
		this.registered = registered;
		this.state = state;
		this.address = address;
	}

	public User(String email, String firstName, String lastName, String timezone, String password) {
		this(firstName, lastName, password, email, timezone, null, UserState.NOT_REGISTERED, null);
	}

	public void setState(UserState state) {
		this.state = state;
	}
}
