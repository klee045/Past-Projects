package com.users;

public class Admin extends User implements java.io.Serializable {

	/** The name. */
	private String name;

	/**
	 * Constructor for Admin
	 *
	 * @param username the username
	 * @param password the password
	 * @param accessPeriodStart start of access period
	 * @param accessPeriodEnd end of access period
	 * @param name name of admin
	 */
	public Admin(String username, String password, String accessPeriodStart, String accessPeriodEnd,String name)
	{
		super(username,password,accessPeriodStart,accessPeriodEnd);
		this.name=name;
		//this.password=password;
	}

	/**
	 * Getter for admin's name
	 *
	 * @return the name of admin
	 */
	public String getName() {
		return name;
	}
}
