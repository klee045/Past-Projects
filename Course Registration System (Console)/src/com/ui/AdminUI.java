package com.ui;

import com.functions.AdminFunctions;
import com.course.*;
import com.courseManager.CourseManager;
import com.login.Login;
import com.studentManager.Studentmgr;
import com.users.*;
import com.database.*;

import java.time.DateTimeException;
import java.util.*;

import java.time.DateTimeException;
import java.util.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class AdminUI implements StrategyUI{

	/** The exit string to allow user to exit out of any function, cannot be edited in other functions. */
	//exitString should always be in lowercase. exitString is the string to be entered in order to exit any function during use
	final String exitString = "exit".toLowerCase();

	/**
	 * Part of the strategy pattern.
	 * Start method is common across all UIs.
	 * Starts the userinterface.
	 * Contains functions from StudentFunctions
	 * Purpose is also to sanitize the inputs from the user to the minimum necessary requirement(e.g username must start with u)
	 * @param u the user object
	 * @param studentDB the student database object
	 * @param courseDB the course database object
	 * @param adminDB the admin database object
	 */
	//method to call to start UI
	public void start(User u, StudentDB studentDB, CourseDB courseDB, AdminDB adminDB) {
		accountDetails();
//		printMenu();
		int choice = 0;
		AdminFunctions adminFunctions = new AdminFunctions(new CourseManager(courseDB), new Studentmgr(studentDB));
		Scanner sc = new Scanner(System.in);
		//List<Course> inCourseList;
		//Course inCourse;
		//Student inStudent;
		//String inLine;
		while (choice !=8 && choice != 9) {
			printMenu();
			System.out.printf("Please select one of the functions<1-9>: ");
			try {
				choice=Integer.parseInt(sc.next());
			}
			catch(NumberFormatException e){
				System.out.println("Please key in integers only." );
				choice = 0;
			}
			switch (choice) {
				case 0:
					break;
				case 1:
					System.out.println("You have selected <1> Edit Student Access Period");
					System.out.printf("Please enter student matric number:");
					String inLine = sc.next();
					System.out.printf("Please enter new beginning date(e.g 20201030):");
					String AccPerStart = sc.next();
					System.out.printf("Please enter new ending date(e.g 20201030):");
					String AccPerEnd = sc.next();
					try {
						if(adminFunctions.editAccessPeriod(inLine.toUpperCase(),AccPerStart,AccPerEnd))System.out.println("Edit successful");
						else System.out.println("Student Not Found.");
					} catch (DateTimeException e) {
						System.out.println("Please enter the proper date format.");
					}
					break;
				case 2:
					System.out.println("You have selected <2> Add A Student");
					printExit();
					System.out.print("Please enter new student username/Matric No.:");
					String username = sc.next();
					while (!username.toLowerCase().startsWith("u") || studentDB.findStudentByUsername(username) != null) {
						if (!username.toLowerCase().startsWith("u")) System.out.println("Username must start with U.");
						else System.out.println("Username is taken, please try another.");
						System.out.print("Please enter new student username/Matric No.:");
						username = sc.next();
					}
					System.out.print("Please enter new student password:");
					String password = sc.next();
					System.out.print("Please enter new student accessPeriodStart(e.g 20201030):");
					String accessPeriodStart = sc.next();
					System.out.print("Please enter new student accessPeriodEnd(e.g 20201030):");
					String accessPeriodEnd = sc.next();
//				System.out.print("Please enter new student matricNo:");
//				String matricNo = sc.next();
//				while (!matricNo.toLowerCase().startsWith("u")) {
//					if (!matricNo.toLowerCase().startsWith("u")) System.out.println("matricNo must start with U.");
//					System.out.print("Please enter new student matricNo:");
//					matricNo = sc.next();
//				}
					System.out.print("Please enter new student name:");
					sc.nextLine();
					String name = sc.nextLine();
					System.out.print("Please enter new student gender:");
					String gender = sc.next();
					while (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female") && !gender.equalsIgnoreCase("neutral")) {
						System.out.println("Only male, female or neutral is allowed");
						System.out.print("Please enter new student gender:");
						gender = sc.next();
					}
					System.out.print("Please enter new student nationality:");
					String nationality = sc.next();
					System.out.print("Please enter new student school:");
					String school = sc.next();
					System.out.print("Please enter new student email:");
					String email = sc.next();
					while (!isEmail(email)) {
						System.out.println("Invalid email address given, please try again.");
						System.out.print("Please enter new student email:");
						email = sc.next();
					}
					try {
						adminFunctions.addStudent(username, Login.genHashPass(password), accessPeriodStart, accessPeriodEnd, name,gender,nationality,school,email);
						System.out.println("Successfully added.");
						printAllStudents(studentDB);
					} catch (DateTimeException e) {
						System.out.println("Unable to add new student, please enter the proper date format.");
					} catch (Exception ex) {
						System.out.println("Oops, something went wrong. Please try again.");
					}
					break;
				case 3:
					System.out.println("You have selected <3> Add A Course");
					System.out.print("Please enter new course code:");
					String course_code = sc.next();
					System.out.print("Please enter new course name:");
					sc.nextLine();
					String course_name = sc.nextLine();
					int course_AU = -1;
					while (course_AU <0) {
						try {
							System.out.print("Please enter new course AU:");
							course_AU=Integer.parseInt(sc.next());
						}
						catch(NumberFormatException e){
							System.out.println("Please key in integers only.");
							course_AU = -1;
						}
					}
					try {
						adminFunctions.addNewCourse(course_code,course_name,course_AU);
						int num = -1;
						while (num <0) {
							try {
								System.out.print("Please enter the number of course index(es) for this course:");
								num =Integer.parseInt(sc.next());
							}
							catch(NumberFormatException e){
								System.out.println("Please key in integers only.");
								num = -1;
							}
						}
						for(int i=0;i<num;i++)
						{
							System.out.print("Please enter new course index code:");
							String course_index_code = sc.next();
							int total_slot = -1;
							while (total_slot < 0) {
								try {
									System.out.print("Please enter total slots:");
									total_slot =Integer.parseInt(sc.next());
								}
								catch(NumberFormatException e){
									System.out.println("Please key in integers only.");
									total_slot = -1;
								}
							}
							System.out.println("Please enter the time slot");
							System.out.println("day lessonStart-lessonEnd,day lessonStart-lessonEnd:");
							sc.nextLine();
							int[][] timeslot = generateTimetable(sc.nextLine());
							adminFunctions.addNewCourseIndex(course_code, course_index_code, total_slot, timeslot);
						}
						System.out.println("Successfully added.");
						printAllCourses(courseDB);
					}catch(CourseCodeExistsException e){
						System.out.println("Course Code already in use");
					}
					catch (Exception e) {
						System.out.println("Unable to add new course, please try again.");
					}
					break;
				case 4:
					System.out.println("You have selected <4> Update A Course");
					System.out.print("Please enter course code:");
					course_code = sc.next();
					while (courseDB.findCourse(course_code) == null) {
						System.out.println("Course is not found, please try again");
						System.out.print("Please enter course code:");
						course_code = sc.next();
					}
//			    if(courseDB.findCourse(inLine)==null)
//			    {
//			    	System.out.println("Course not found.");
//			    	break;
//			    }
					int choice2 = 0;
					do{
						printCourseMenu();
						try {
							choice2=Integer.parseInt(sc.next());
						}
						catch(NumberFormatException e){
							System.out.println("Please key in integers only." );
							choice2 = 0;
						}
						switch(choice2){
							case 0:
								break;
							case 1:
								System.out.print("Please enter the new course code:");
								String new_course_code = sc.next();
								course_code = adminFunctions.updateCourseCode(course_code,new_course_code);
								break;
							case 2:
								System.out.print("Please enter the old course index:");
								String old = sc.next();
								System.out.print("Please enter the new course index:");
								String newIndex = sc.next();
								adminFunctions.updateCourseIndex(course_code,old,newIndex);
								break;
							case 3:
								System.out.print("Please enter the course index:");
								String index = sc.next();
								System.out.print("Please enter the total slots:");
								int total_slots = sc.nextInt();
								adminFunctions.updateCourseVacancy(course_code,index,total_slots);
								break;
							case 4:
								System.out.print("Please enter the new course index:");
								index = sc.next();
								System.out.print("Please enter the total slots:");
								total_slots = sc.nextInt();
								System.out.println("Please enter the time slot");
								System.out.println("day lessonStart-lessonEnd,day lessonStart-lessonEnd:");
								sc.nextLine();
								int[][] timeslot = generateTimetable(sc.nextLine());
								if(!adminFunctions.checkIndexExists(course_code, index)){
									try{
										adminFunctions.addNewCourseIndex(course_code, index, total_slots, timeslot);
									}catch(Exception e){
										System.out.println(e);
									}
								}else{
									System.out.println("Course Index Already Exists");
								}
								break;
						}
					}while(choice2 != 5);
					break;
				case 5:
					System.out.println("You have selected <5> Check Available Slot For An Index Number");
					System.out.printf("Please enter course code:");
					course_code = sc.next();
					System.out.printf("Please enter course index:");
					String course_index = sc.next();
					System.out.println("Vacancy left:"+adminFunctions.checkClassVacancy(course_code,course_index));
					break;
				case 6:
					System.out.println("You have selected <6> Print student list by index number");
					System.out.printf("Please enter course code:");
					course_code = sc.next();
					System.out.printf("Please enter course index:");
					course_index = sc.next();
					adminFunctions.printStudentListByIndex(course_code,course_index);
//				inCourse = courseDB.findCourse(inLine);
//				if (inCourse == null) System.out.println("Error! Course not found!");
//				else adminFunctions.printStudentListByIndex(inCourse);
					break;
				case 7:
					System.out.println("You have selected <7> Print student list by course");
					System.out.printf("Please enter course code:");
					course_code = sc.next();
					adminFunctions.printStudentListByCourse(course_code);
					break;
				case 8:
					System.out.println("Exiting program...");
					break;
				case 9:
					System.out.println("Returning to login screen...");
					User u2 = Login.getInstance().open(studentDB, adminDB);
					UIFactory factory = new UIFactory();
					ContextUI ui = factory.getUI(u2);
					ui.start(u2, studentDB, courseDB, adminDB);
					break;
				case 10:
					System.out.println("Students:");
					for(Student s: studentDB.getStudentList())
					{
						System.out.printf(s.getMatricNo()+" ");
						System.out.printf(s.getName()+" ");
						System.out.printf(s.getGender()+" ");
						System.out.printf(s.getNationality()+" ");
						for(String j:s.getCourseEnrolled())
						{
							System.out.printf(j+" ");
						}
						System.out.printf("\n");
					}
					System.out.println("Admins:");
					for(Admin a:adminDB.getAdminList())
					{
						System.out.printf(a.getName()+" ");
						System.out.printf("\n");
					}
					System.out.println("Courses:");
					for(Course a:courseDB.getCourseList())
					{
						System.out.printf(a.getCode()+"\n");
						for(CourseIndex j : a.getIndex())
						{
							System.out.printf("Printing Students in this index "+j.getCode()+" : ");
							for(String s2 : j.getStudents())
							{
								System.out.print(s2+" ");
							}
							System.out.println();
							System.out.printf("Printing Students in the waiting list of this index "+j.getCode()+" : ");
							for(String s2 : j.getWaitingList())
							{
								System.out.print(s2+" ");
							}
							System.out.println();
						}
						System.out.printf("\n");
					}
					break;
				default:
					System.out.println("Incorrect option, please select options 1-9!");

			}
		}
	}

	/**
	 * Prints the menu.
	 * For ease of changing menu and prevent duplicate code when printing in multiple areas
	 */
	private void printMenu() {
		/*
		 * System.out.println("===          ==     ===============     ==           =="
		 * );
		 * System.out.println("====         ==           ===           ==           =="
		 * );
		 * System.out.println("==  ==       ==           ===           ==           =="
		 * );
		 * System.out.println("==   ==      ==           ===           ==           =="
		 * );
		 * System.out.println("==    ==     ==           ===           ==           =="
		 * );
		 * System.out.println("==     ==    ==           ===           ==           =="
		 * );
		 * System.out.println("==      ==   ==           ===           ==           =="
		 * );
		 * System.out.println("==       ==  ==           ===           ==           =="
		 * );
		 * System.out.println("==        == ==           ===           ==           =="
		 * );
		 * System.out.println("==         ====           ===            ==         == "
		 * );
		 * System.out.println("==          ===           ===             ===========  "
		 * );
		 */
		System.out.println();
		System.out.println("	====================Menu====================\n"
				+ "	1. Edit student access period\n"
				+ "	2. Add a student\n"
				+ "	3. Add a course\n"
				+ "	4. Update a course\n"
				+ "	5. Check available slot for an index number\n"
				+ "	6. Print student list by index number\n"
				+ "	7. Print student list by course\n"
				+ "	8. Exit\n"
				+ "	9. Login as another user\n");
		// + "    10. Display All Details\n");
	}

	/**
	 * Prints the account details of the user.
	 * To be displayed upon logging into the userinterface.
	 */
	private void printCourseMenu() {
		System.out.println("	====================Menu====================\n"
				+ "	1. Change Course Code\n"
				+ "	2. Change Course Index\n"
				+ "	3. Change Course Index Vacancy\n"
				+ "	4. Add a New Course Index\n"
				+ "	5. Exit Course Edit Menu\n");
		System.out.printf("Please select one of the functions<1-5>: ");
	}

	/**
	 * Prints the account details of the user
	 */
	//
	private void accountDetails() {
		System.out.println("You are currently logged in to: Admin");
	}

	/**
	 * Checks whether the string given is equal to the exitString
	 * @param input input of the user
	 * @return true if input is equal exit string
	 */
	private boolean isExit(String input) {
		if (input.strip().toLowerCase().contains(exitString)) return true;
		else return false;
	}

	/**
	 * Prints the line to inform user to enter the exit string.
	 * To prevent duplicate code.
	 */
	//prints the exitString for user to key in to exit
	private void printExit() {
		System.out.printf("To exit please enter %s\n", exitString);
	}

	/**
	 * Generate timetable.
	 *
	 * @param courseTiming the course timing
	 * @return the timetable that corresponds to the time that the user entered.
	 */
	public static int[][] generateTimetable(String courseTiming){
		// courseTiming format "day lessonStart-lessonEnd,day lessonStart-lessonEnd"
		// Example "0 15-16,2 24-29" is equal to "Monday 0730-0830, Wednesday 1200-1500
		int date,lessonStart,lessonEnd;
		int[][] CourseTimetable = new int[48][7];
		String[] LessonSlots = courseTiming.split(",");

		for(int i=0;i<LessonSlots.length;i++){
			date = Integer.parseInt(LessonSlots[i].substring(0,1));
			lessonStart = Integer.parseInt(LessonSlots[i].substring(2,4)) ;
			lessonEnd = Integer.parseInt(LessonSlots[i].substring(5,7)) ;
			for(int j=lessonStart;j<=lessonEnd;j++){
				CourseTimetable[j][date]=1;
			}
		}
		return CourseTimetable;
	}
	/**
	 * Checks whether email is a valid email address
	 *
	 * @param email the email address
	 * @return true if email is valid
	 */
	//Checks whether a given string is a valid email address
	//Implementation details is found on https://www.programcreek.com/java-api-examples/?api=javax.mail.internet.InternetAddress
	private static boolean isEmail(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	/**
	 * Print all the students in the database
	 * @param studentDB
	 */
	private void printAllStudents(StudentDB studentDB) {
		System.out.println("Students:");
		for(Student s: studentDB.getStudentList())
		{
			System.out.printf(s.getMatricNo()+" ");
			System.out.printf(s.getName()+" ");
			System.out.printf(s.getGender()+" ");
			System.out.printf(s.getNationality()+" ");
			for(String j:s.getCourseEnrolled())
			{
				System.out.printf(j+" ");
			}
			System.out.printf("\n");
		}
	}

	/**
	 * Print all the courses in the course database
	 * @param courseDB
	 */
	private void printAllCourses(CourseDB courseDB) {
		System.out.println("Courses:");
		for(Course a:courseDB.getCourseList())
		{
			System.out.printf(a.getCode()+"\n");
			for(CourseIndex j : a.getIndex())
			{
				System.out.printf("Printing Students in this index "+j.getCode()+" : ");
				for(String s2 : j.getStudents())
				{
					System.out.print(s2+" ");
				}
				System.out.println();
				System.out.printf("Printing Students in the waiting list of this index "+j.getCode()+" : ");
				for(String s2 : j.getWaitingList())
				{
					System.out.print(s2+" ");
				}
				System.out.println();
			}
			System.out.printf("\n");
		}
	}
}
