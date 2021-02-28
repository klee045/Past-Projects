package com.login;

import java.io.Console;
import java.util.*;

import com.users.Admin;
import com.users.Student;
import com.users.User;
import com.database.AdminDB;
import com.database.StudentDB;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;

public class Login {

	/** Instance for singleton pattern */
	//Singleton pattern as only 1 Login needed
	private static Login instance = new Login();

	/**
	 * Instantiates new login class
	 */
	public Login() {}

	/**
	 * Gets the single instance of Login.
	 * @return single instance of Login.
	 */
	public static Login getInstance() {
		return instance;
	}

	/**
	 * Open. To load the logging in of the student
	 * @param studentDB the student DB
	 * @param adminDB the admin DB
	 * @return the user
	 */
	//The UI of the login class
	public User open(StudentDB studentDB, AdminDB adminDB) {
		Scanner sc = new Scanner(System.in);
		Console console = System.console();
		System.out.printf("Enter Username:");
		String username = sc.next();
	char[] cpassword = console.readPassword("Enter Password: ");
		String password = String.valueOf(cpassword);
//		System.out.printf("Enter Password:");
//		String password = sc.next();
		User u;
		u = validate(username, password, studentDB, adminDB);
		while(u == null) {
			System.out.printf("Enter Username:");
			username = sc.next();
			cpassword = console.readPassword("Enter Password: ");
			password = String.valueOf(cpassword);
//			System.out.printf("Enter Password:");
//    		password = sc.next();
			u = validate(username, password, studentDB, adminDB);
		}
		System.out.println("Logging in...");
		return u;

	}

	/**
	 * Generate hash pass. Static method to be applied for generating hash password with gensalt(12)
	 * prevent duplicate code and unnecessary importing/dependency on jBCrypt for other packages
	 * @param inputPass the password entered by the user
	 * @return hashed string inputPass
	 */
	//Static method for generating hash password with gensalt(12)
	public static String genHashPass(String inputPass) {
		String hashed = BCrypt.hashpw(inputPass, BCrypt.gensalt(12));
		return hashed;
	}

	/**
	 * Validate. Validates whether the user is a student, etc.
	 * @param inUsername the username of the student
	 * @param inPassword the password of the student
	 * @param studentDB the student database object
	 * @param adminDB the admin database object
	 * @return the user found or null if no user found
	 */
	//Validate function for validating a user
	private User validate(String inUsername, String inPassword, StudentDB studentDB, AdminDB adminDB) {
		List<Student> studentList = studentDB.getStudentList();
		List<Admin> adminList = adminDB.getAdminList();
		LocalDate today = LocalDate.now();

		for (Admin admin : adminList) {
			if (BCrypt.checkpw(inPassword, admin.getPassword()) && admin.getUsername().toLowerCase().equals(inUsername.toLowerCase())) return admin;
		}
		for (Student student : studentList) {
			if (BCrypt.checkpw(inPassword, student.getPassword()) && student.getUsername().toLowerCase().equals(inUsername.toLowerCase())) {
					if (!student.getAccPerStart().isAfter(today) && !student.getAccPerEnd().isBefore(today))return student;
					else {
						System.out.println("You are not able to access STARS now.");
						System.out.printf("Your access period is from %s to %s.\n",User.convertDate(student.getAccPerStart()), User.convertDate(student.getAccPerEnd()));
						return null;
					}
				}
		}
		System.out.println("Username and password invalid, please try again.\n");
		return null;
	}



}
