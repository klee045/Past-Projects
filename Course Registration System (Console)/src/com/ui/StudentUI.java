package com.ui;

import com.course.Course;
import com.course.CourseIndex;
import com.courseManager.CourseManager;
import com.login.Login;
import com.functions.StudentFunctions;
import com.studentManager.Studentmgr;
import com.users.Admin;
import com.users.Student;
import com.users.User;
import com.database.AdminDB;
import com.database.CourseDB;
import com.database.StudentDB;

import java.util.List;
import java.util.Scanner;

public class StudentUI implements StrategyUI{

	/** The exit string to allow user to exit out of any function, cannot be edited in other functions. */
	// exitString should always be in lowercase. exitString is the string to be entered in order to exit any function during use
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
	// method to call to start interface
	public void start(User u, StudentDB studentDB, CourseDB courseDB, AdminDB adminDB) {

		Student s = (Student) u;
		accountDetails(s);
//		printMenu();
		int choice =0;
		StudentFunctions studentFunctions = new StudentFunctions(new CourseManager(courseDB), new Studentmgr(studentDB));
		Scanner sc = new Scanner(System.in);
		List<String> courseEnrolled;
		String inCourseCode;
		String inCourseCodeIndex;
		String inCourseCodeIndex2;
		String inMatricNo = s.getMatricNo();
		String inMatricNo2;
		int courseVacancy;

		while (choice != 10 && choice != 11 ) {
			printMenu();
			System.out.printf("Please select one of the functions<1-11>: ");
			try {
		        choice=Integer.parseInt(sc.next());
		    }
		    catch(NumberFormatException e){
		        System.out.println("Please key in integers only." );
		        choice = 0;
		    }
			switch (choice) {
			//for when a non-integer is filled;
			case 0:
				break;
			//Print all courses available in courseDB
			case 1:
				System.out.printf("Course available are:\n");
				for (String course : studentFunctions.getAllCourses()) {
					System.out.printf("Course: %s Index: ",course);
					for (String index : studentFunctions.getAllIndex(course)){
						System.out.printf("%s ", index);
					}
					System.out.println();
				}
				System.out.println();
				break;
			//Register user for a course
			case 2:
				try {
					System.out.println("You have selected <2> Register For A Course.");
					printExit();
					System.out.printf("Please enter Course Code Index(e.g CZ2002/01):");
					inCourseCodeIndex = sc.next();
					if (isExit(inCourseCodeIndex)) {
						System.out.println("Exiting...");
						break;
					}
					int result = studentFunctions.addCourse(inMatricNo, inCourseCodeIndex);
					while (result!= 1 && result!=2) {
						if(result==3){
							System.out.println("New course clashes with current registered courses!");
						}
						else if(result==4){
							System.out.println("Invalid Index!");
						}
						else if(result==5){
							System.out.println("AU Limit exceeded!");
						}
						else if(result==6){
							System.out.println("Student is already taking the course!");
						}
						else if(result==7){
							System.out.println("Student is already in waiting list for the course!");
						}

						System.out.println("Unsuccessful, please try again");
						System.out.printf("Please enter Course Code Index(e.g CZ2002/01):");
						inCourseCodeIndex = sc.next();
						if (isExit(inCourseCodeIndex)) {
							System.out.println("Exiting...");
							break;
						}
						result = studentFunctions.addCourse(inMatricNo, inCourseCodeIndex);
					}
						if (result== 1) {
							System.out.println("Successfully added!");
							studentFunctions.printCourse(inMatricNo);
						}
						else if (result== 2) System.out.println("Successfully added into waiting list!");
						break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}

			//Drop user from a course
			case 3:
				try {
					System.out.println("You have selected <3> Drop A Course.");
					printExit();
					System.out.printf("Please enter Course Code Index(e.g CZ2002/01):");
					inCourseCodeIndex = sc.next();
					if (isExit(inCourseCodeIndex)) {
						System.out.println("Exiting...");
						break;
					}
					while (!studentFunctions.dropCourse(inMatricNo, inCourseCodeIndex)) {
						System.out.println("Unsuccessful, please try again");
						System.out.printf("Please enter Course Code Index(e.g CZ2002/01):");
						inCourseCodeIndex = sc.next();
						if (isExit(inCourseCodeIndex)) {
							System.out.println("Exiting...");
							break;
						}
					}
					if (!isExit(inCourseCodeIndex)) System.out.println("Successfully dropped!");
					break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}
			//Check all courses registered
			case 4:
				try
				{
					System.out.println("You have selected <4> Check/Print Courses Registered.");
					courseEnrolled = s.getCourseEnrolled();
					System.out.printf("Courses are:");
					for (String course : courseEnrolled) {
						System.out.printf("%s ", course);
					}
					System.out.println();
					break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}
			//Check vacancies by course code
			case 5:
				try
				{
					System.out.println("You have selected <5> Check Vacancies Available By Course.");
					System.out.printf("Please enter course number(e.g CZ2002):");
					inCourseCode = sc.next();
					while (inCourseCode.length() <6) {
						System.out.println("Please enter course number in proper format(e.g CZ2002/01)");
						System.out.printf("Please enter course number(e.g CZ2002/01):");
						inCourseCode = sc.next();
					}
					courseVacancy = studentFunctions.checkVacancyCourse(inCourseCode);

					if (courseVacancy == -1) System.out.println("Error! Course not found!");
					else System.out.printf("Vacancies: " + courseVacancy+ "\n");
					break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}
			//Check vacancies by course indexU
			case 6:
				try
				{
					System.out.println("You have selected <6> Check Vacancies Available By Course Index.");
					System.out.printf("Please enter Course Code Index(e.g CZ2002/01):");
					inCourseCodeIndex = sc.next();
					while (inCourseCodeIndex.length() <9) {
						System.out.println("Please enter course number in proper format(e.g CZ2002/01)");
						System.out.printf("Please enter course number(e.g CZ2002/01):");
						inCourseCodeIndex = sc.next();
					}
					courseVacancy = studentFunctions.checkVacancyCourseIndex(inCourseCodeIndex);
					if (courseVacancy == -1) System.out.println("Error! Course not found!");
					else System.out.printf("Vacancies: " + courseVacancy + "\n");
					break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}
			//Change course index for user
			case 7:
				try
				{
					System.out.println("You have selected <7> Change Index Number of Course.");
					printExit();
					System.out.printf("Please enter Course Code Index to drop(e.g CZ2002/01):");
					inCourseCodeIndex = sc.next();
					if (isExit(inCourseCodeIndex)) {
						System.out.println("Exiting...");
						break;
					}
					System.out.printf("Please enter Course Code Index to add(e.g CZ2002/01):");
					inCourseCodeIndex2 = sc.next();
					if (isExit(inCourseCodeIndex2)) {
						System.out.println("Exiting...");
						break;
					}
					if (!studentFunctions.changeIndex(inMatricNo, inCourseCodeIndex, inCourseCodeIndex2)) System.out.println("Index change is unsuccessful");
					else System.out.println("Index has been swapped.");
					break;
				}
				//TODO confirm purpose of course code(2nd param) in change index function
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}
			//Swop course index with another user
			case 8:
				try
				{
					System.out.println("You have selected <8> Swap Index Number With Another Student.");
					printExit();
					System.out.printf("Please enter 2nd student matriculation number:");
					inMatricNo2 = sc.next();
					if (isExit(inMatricNo2)) {
						System.out.println("Exiting...");
						break;
					}
					System.out.printf("Please enter your Course Code Index to be swapped(e.g CZ2002/01):");
					inCourseCodeIndex = sc.next();
					if (isExit(inCourseCodeIndex)) {
						System.out.println("Exiting...");
						break;
					}
					System.out.printf("Please enter other student's Course Code Index to be swapped(e.g CZ2002/01):");
					inCourseCodeIndex2 = sc.next();
					if (isExit(inCourseCodeIndex2)) {
						System.out.println("Exiting...");
						break;
					}
					if (!studentFunctions.swopIndex(inMatricNo, inMatricNo2, inCourseCodeIndex, inCourseCodeIndex2)) System.out.println("Index swop is unsuccessful.");
					else System.out.println("Index has been swapped with the other student.");
					break;
				}
				catch(Exception e)
				{
					System.out.println("Something went wrong, please try again");
					break;
				}

			//Allow user to exit program
			case 9:
				System.out.println("You have selected <11> Print Timetable.");
				System.out.printf("Total AU: %d%n",s.getTotalAU());
				if(!studentFunctions.printTimetable(s.getMatricNo())){
					System.out.println("Student not found!");
				};
				break;
			case 10:
				System.out.println("Exiting program...");
				break;
			//Allow user to re-login to another account
			case 11:
				System.out.println("Returning to login screen...");
				User u2 = Login.getInstance().open(studentDB, adminDB);
				UIFactory factory = new UIFactory();
				ContextUI ui = factory.getUI(u2);
				ui.start(u2, studentDB, courseDB, adminDB);
				break;

			case 12:
				System.out.println("Students:");
			    for(Student s1: studentDB.getStudentList())
			    {
			    	System.out.printf(s1.getMatricNo()+" ");
			    	System.out.printf(s1.getName()+" ");
			    	System.out.printf(s1.getGender()+" ");
			    	System.out.printf(s1.getNationality()+" ");
			    	for(String j:s1.getCourseEnrolled())
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
			//Default statement for all integers not 1-10
			default:
				System.out.println("Incorrect option, please select options 1-11!");

			}
		}
	}

	/**
	 * Prints the menu.
	 * For ease of changing menu and prevent duplicate code when printing in multiple areas
	 */
	//Prints the menu of options <- change this function to change the menu
	private void printMenu() {
//		System.out.println();
//		System.out.println("===          ==     ===============     ==           ==");
//		System.out.println("====         ==           ===           ==           ==");
//		System.out.println("==  ==       ==           ===           ==           ==");
//		System.out.println("==   ==      ==           ===           ==           ==");
//		System.out.println("==    ==     ==           ===           ==           ==");
//		System.out.println("==     ==    ==           ===           ==           ==");
//		System.out.println("==      ==   ==           ===           ==           ==");
//		System.out.println("==       ==  ==           ===           ==           ==");
//		System.out.println("==        == ==           ===           ==           ==");
//		System.out.println("==         ====           ===            ==         == ");
//		System.out.println("==          ===           ===             ===========  ");
//		System.out.println();
		System.out.println("	====================Menu====================\n"
						 + "	1. Check Course Details\n"
						 + "	2. Register For A Course\n"
						 + "	3. Drop A Course\n"
						 + "	4. Check/Print Courses Registered\n"
					 	 + "	5. Check Vacancies Available By Course\n"
					 	 + "	6. Check Vacancies Available By Course Index\n"
					 	 + "	7. Change Index Number of Course\n"
					 	 + "	8. Swap Index Number With Another Student\n"
						 + "	9. Print Timetable\n"
					 	 + "	10. Exit The Program\n"
					 	 + "	11. Login With Another Account\n");


	}

	/**
	 * Prints the account details of the user.
	 * To be displayed upon logging into the userinterface.
	 * @param u the user object
	 */
	//Prints the account details of the user
	private void accountDetails(Student u) {
			System.out.printf("You are currently logged in to: %s, Matric:%s\n", u.getName(), u.getMatricNo());
			System.out.printf("Email: %s\n",u.getEmail());
		}

	/**
	 * Checks if input string equals exit string.
	 * @param input the input of the user
	 * @return true if input is equal exit string
	 */
	//Checks whether the string given is equal to the exitString
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


}
