package com.ui;

import com.users.User;
import com.database.AdminDB;
import com.database.CourseDB;
import com.database.StudentDB;

public class ContextUI {

	/** The login type. */
	private StrategyUI loginType;

	/**
	 * Sets the strategy UI.
	 *
	 * @param loginType the new strategy UI
	 */
	public void setStrategyUI(StrategyUI loginType) {
		this.loginType = loginType;
	}

	/**
	 * Start the application.
	 * This function generates the interface according to the user oject that has been passed into it.
	 *
	 * @param u the user reference object. The user will be created by the UIFactory.
	 * @param studentDB the studentDatabase
	 * @param courseDB the courseDatabase
	 * @param adminDB the adminDatabase
	 */
	public void start(User u, StudentDB studentDB, CourseDB courseDB, AdminDB adminDB) {
		loginType.start(u, studentDB, courseDB, adminDB);
	}
}
