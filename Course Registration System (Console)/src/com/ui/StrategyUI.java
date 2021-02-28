package com.ui;

import com.users.User;
import com.database.AdminDB;
import com.database.CourseDB;
import com.database.StudentDB;

public interface StrategyUI {

	/**
	 * This function starts the application and generates the user interface.
	 *
	 * @param u the user object
	 * @param studentDB the student database object
	 * @param courseDB the course database object
	 * @param adminDB the admin database object
	 */
	public void start(User u, StudentDB studentDB, CourseDB courseDB, AdminDB adminDB);
}
