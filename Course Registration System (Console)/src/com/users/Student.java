package com.users;

import com.notification.gmailNotif;
import com.notification.notifListener;

import java.util.*;

public class Student extends User implements java.io.Serializable  {

	/** The matric no. */
	private String matricNo;

	/** The name. */
	private String name;

	/** The gender. */
	private String gender;

	/** The nationality. */
	private String nationality;

	/** The school. */
	private String school;

	/** The email. */
	private String email;

	/** The preferred notification. */
	private String preferredNotification = "mail";

	/** The AU. */
	private int totalAU;

	/** The timetable. */
	public int[][] timetable = new int[48][7];

	/** The course enrolled. */
	private List<String> courseEnrolled = new ArrayList<String>();

	/** The course waitlist. */
	private List<String> courseWaitlist = new ArrayList<String>();


	/**
	 * Constructor for student class without waiting list
	 * @param username the username
	 * @param password the password
	 * @param accessPeriodStart the access period start date (format: YYYYMMDD)
	 * @param accessPeriodEnd the access period end date (format: YYYYMMDD)
	 * @param name the name of the student
	 * @param gender the gender
	 * @param nationality the nationality
	 * @param school the school
	 * @param email the email address
	 */
	public Student(String username, String password, String accessPeriodStart, String accessPeriodEnd,String name,String gender,String nationality,String school,String email)
	{
		super(username,password,accessPeriodStart,accessPeriodEnd);
		this.matricNo=username;
		this.name=name;
		this.gender=gender;
		this.nationality=nationality;
		this.school = school;
		this.totalAU = 0;
		this.email = email;
	}

	/**
	 * Constructor for student class with waiting list
	 * @param username the username
	 * @param password the password
	 * @param accessPeriodStart the access period start date (format: YYYYMMDD)
	 * @param accessPeriodEnd the access period end date (format: YYYYMMDD)
	 * @param name the student's name
	 * @param gender the gender
	 * @param nationality the nationality
	 * @param school the school
	 * @param email the email
	 * @param courseWaitlist the course indexes where the student is in its waiting list
	 */
	// if waitlist is not empty
	public Student(String username, String password, String accessPeriodStart, String accessPeriodEnd,String name,String gender,String nationality,String school,String email,List<String> courseWaitlist)
	{
		super(username,password,accessPeriodStart,accessPeriodEnd);
		this.matricNo=username;
		this.name=name;
		this.gender=gender;
		this.nationality=nationality;
		this.school = school;
		this.totalAU = 0;
		this.email = email;
		this.courseWaitlist = courseWaitlist;
	}

	/**
	 * Register.
	 *
	 * @param CourseIndex_string the course index code
	 */
	public void register(String CourseIndex_string)
	{
		courseEnrolled.add(CourseIndex_string);
	}

	/**
	 * Update timetable.
	 *
	 * @param choice the choice 1 - add, 0 - drop
	 * @param courseIndexTimetable the course index timetable
	 */
	public void updateTimetable(int choice, int[][] courseIndexTimetable) {
		// choice: 1 = add and 0 = drop
		if (choice == 1) {
			for (int session = 0; session < 48; session++) {
				for (int day = 0; day < 7; day++) {
					timetable[session][day] = timetable[session][day] + courseIndexTimetable[session][day];
				}
			}
		} else {
			for (int session = 0; session < 48; session++) {
				for (int day = 0; day < 7; day++) {
					timetable[session][day] = timetable[session][day] - courseIndexTimetable[session][day];
				}
			}
		}
		return;
	}

	/**
	 * Sets the notification preference.
	 *
	 * @param type the new notification preference
	 */
	public void setNotifPreference(String type){
		if(type.contains("mail")){
			preferredNotification = "mail";
			return;
		}
		System.out.println("Unknown type!");
		return;
	}

	/**
	 * Sets the email address
	 *
	 * @param emailAddress the new email
	 */
	public void setEmail(String emailAddress){
		email = emailAddress;
	}

	/**
	 * Change AU.
	 *
	 * @param AU the AU of the studnet
	 * @param choice the choice 1 - add, 0 - drop.
	 */
	public void changeAU(int AU,int choice){
		if(choice==1) {
			totalAU += AU;
		}
		else{
			totalAU -= AU;
		}
		return;
	}

	/**
	 * update the course index that are in the student's waiting list
	 *
	 * @param CourseCodeIndex to update to waiting list
	 * @param choice the choice 1- add, 0 - remove
	 */
	public void updateWaitlist(String CourseCodeIndex,int choice){
		// 1 == add
		// 0 == remove
		if(choice == 1){
			courseWaitlist.add(CourseCodeIndex);
		}
		else{
			courseWaitlist.remove(CourseCodeIndex);
		}
	}

	/**
	 * Gets the waiting list.
	 *
	 * @return the course indexes that the student is in its waiting list
	 */
	public List<String> getCourseWaitlist() {
		return courseWaitlist;
	}

	/**
	 * Gets the timetable.
	 *
	 * @return the timetable
	 */
	public int[][] getTimetable() {
		return timetable;
	}

	/**
	 * Gets the course enrolled.
	 *
	 * @return the course enrolled
	 */
	public List<String> getCourseEnrolled() {
		return courseEnrolled;
	}

	/**
	 * Gets the total AU.
	 *
	 * @return the total AU
	 */
	public int getTotalAU() {return totalAU;}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Gets the matric no.
	 *
	 * @return the matric no
	 */
	public String getMatricNo() {
		return matricNo;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the nationality.
	 *
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * Gets the notif preference.
	 *
	 * @return the notif preference
	 */
	public String getNotifPreference(){
		return preferredNotification;
	}

}
