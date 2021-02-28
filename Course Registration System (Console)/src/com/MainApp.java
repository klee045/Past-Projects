package com;

import com.login.*;
import com.users.*;
import com.course.*;
import com.database.*;
import com.ui.*;
import com.functions.*;
import com.studentManager.*;
import com.notification.*;

import java.io.*;

/**
 * The Class MainApp.
 *
 */
public class MainApp {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		// feed Data (One time thing)
		DataFeeder.feedData();

		DatabaseIO databaseIO = new DatabaseIO();
		Database database = new Database();
		AdminDB adminDB = null;
		StudentDB studentDB = null;
		CourseDB courseDB = null;

//      Load Data from serialized file
		try {
			database = (Database) databaseIO.readData("database.ser");
			adminDB = database.getAdmindb();
			studentDB = database.getStudentdb();
			courseDB = database.getCoursedb();
		} catch (IOException e) {
			e.printStackTrace();
		}


		User u = Login.getInstance().open(studentDB, adminDB);
		UIFactory factory = new UIFactory();
		ContextUI ui = factory.getUI(u);
		ui.start(u, studentDB, courseDB, adminDB);
		System.out.println("Saving, please do not close the window yet...");

 //     Write data by serialization
		try {
			database.setAdmindb(adminDB);
			database.setCoursedb(courseDB);
			database.setStudentdb(studentDB);
			databaseIO.writeData("database.ser", database);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Program terminated.");
		return;
	}
}

