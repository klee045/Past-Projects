package com;

import com.course.Course;
import com.courseManager.CourseManager;
import com.functions.StudentFunctions;
import com.login.Login;
import com.studentManager.Studentmgr;
import com.users.Admin;
import com.users.Student;
import com.database.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFeeder {

	/**
	 * Feed Preset data.
	 */
	public static void feedData() {
		// Initialize Admin Accounts

		List<Admin> adminList = new ArrayList<Admin>();
		Admin adminAccount1 = new Admin("Admin01", Login.genHashPass("password"), "20201115", "20201125", "Bruce Lee");
		Admin adminAccount2 = new Admin("Admin02", Login.genHashPass("password"), "20201115", "20201125",
				"Jackie Chan");
		adminList.add(adminAccount1);
		adminList.add(adminAccount2);

		// Initialize Student Accounts
		List<Student> studentList = new ArrayList<Student>();
		Student studentAccount1 = new Student("U01", Login.genHashPass("password"), "20201125", "20201225",
				 "TestUser", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount1);
		Student studentAccount2 = new Student("U02", Login.genHashPass("password"), "20201125", "20201225",
				"TestUser2", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount2);
		Student studentAccount3 = new Student("U03", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser3", "Female", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount3);
		Student studentAccount4 = new Student("U04", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser4", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount4);
		Student studentAccount5 = new Student("U05", Login.genHashPass("password"), "20201115", "20201125",
				 "TestUser5", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount5);
		Student studentAccount6 = new Student("U06", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser6", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount6);
		Student studentAccount7 = new Student("U07", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser7", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount7);
		Student studentAccount8 = new Student("U08", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser8", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount8);
		Student studentAccount9 = new Student("U09", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser9", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount9);
		Student studentAccount10 = new Student("U10", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser10", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount10);
		Student studentAccount11 = new Student("U11", Login.genHashPass("password"), "20201115", "20201125",
				 "TestUser11", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount11);
		Student studentAccount12 = new Student("U12", Login.genHashPass("password"), "20201115", "20201125",
				 "TestUser12", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount12);
		Student studentAccount13 = new Student("U13", Login.genHashPass("password"), "20201115", "20201125",
				"TestUser13", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount13);
		Student studentAccount14 = new Student("U14", Login.genHashPass("password"), "20201115", "20201125",
				 "TestUser14", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount14);
		Student studentAccount15 = new Student("U15", Login.genHashPass("password"), "20201201", "20201231",
				 "TestUser15", "Male", "Singaporean", "SCSE","brent.kjy@gmail.com");
		studentList.add(studentAccount15);
		// Initialize Courses
		List<Course> courseList = new ArrayList<Course>();
		Course course1 = new Course("CZ2002", "Object Oriented Programming",4);
		course1.addCourseIndex("01", 10, generateTimetable("0 15-16,2 24-29"));
		course1.addCourseIndex("02", 10, generateTimetable("1 15-16,3 24-29"));
		course1.addCourseIndex("03", 10, generateTimetable("1 18-19,3 30-32")); // clash with CZ2001/02
		Course course2 = new Course("CZ2006", "Software Engineering",4);
		course2.addCourseIndex("01", 10, generateTimetable("0 15-16,2 24-29"));
		course2.addCourseIndex("02", 10, generateTimetable("1 15-16,3 24-29"));
		course2.addCourseIndex("03", 10, generateTimetable("5 15-16,6 24-29"));
		Course course3 = new Course("CZ2001", "Algorithm",4);
		course3.addCourseIndex("01", 10, generateTimetable("0 15-16,2 24-29"));
		course3.addCourseIndex("02", 10, generateTimetable("1 18-19,3 30-32"));
		course3.addCourseIndex("03", 1, generateTimetable("5 15-16,6 24-29"));
		Course course4 = new Course("CZ2007", "Database",4);
		course4.addCourseIndex("01", 10, generateTimetable("5 40-42,6 40-42"));
		course4.addCourseIndex("02", 10, generateTimetable("0 33-35,4 15-16"));
		course4.addCourseIndex("03", 10, generateTimetable("4 33-35,6 33-35"));
		Course course5 = new Course("CZ2005", "Operating Systems",4);
		course5.addCourseIndex("01", 10, generateTimetable("1 33-35,3 36-38"));
		course5.addCourseIndex("02", 10, generateTimetable("5 40-42,6 40-42")); // clashes with CZ2007/01
		course5.addCourseIndex("03", 3, generateTimetable("4 33-35,6 33-35"));
		Course course6 = new Course("CZ2003", "Computer Graphics & Visualisation",4);
		course6.addCourseIndex("01", 10, generateTimetable("2 15-16,2 24-29"));
		course6.addCourseIndex("02", 10, generateTimetable("1 33-35,3 36-38"));
		course6.addCourseIndex("03", 10, generateTimetable("4 33-35,6 33-35"));
		courseList.add(course1);
		courseList.add(course2);
		courseList.add(course3);
		courseList.add(course4);
		courseList.add(course5);
		courseList.add(course6);
;		// Register Courses
		CourseDB courseDB = new CourseDB(courseList);
		StudentDB studentDB = new StudentDB(studentList);
		AdminDB adminDB = new AdminDB(adminList);
		StudentFunctions sf = new StudentFunctions(new CourseManager(courseDB), new Studentmgr(studentDB));
		sf.addCourse("U01", "CZ2002/01");
		sf.addCourse("U01", "CZ2006/02");
		sf.addCourse("U01", "CZ2005/03");
		sf.addCourse("U02", "CZ2002/01");
		sf.addCourse("U03", "CZ2002/02");
		sf.addCourse("U03", "CZ2006/01");
		sf.addCourse("U04", "CZ2002/01");
		sf.addCourse("U04", "CZ2001/02");
		sf.addCourse("U05", "CZ2001/03");
		sf.addCourse("U05", "CZ2002/02");
		sf.addCourse("U01", "CZ2007/01");
		sf.addCourse("U02", "CZ2007/01");
		sf.addCourse("U03", "CZ2007/01");
		sf.addCourse("U04", "CZ2007/01");
		sf.addCourse("U05", "CZ2007/01");
		sf.addCourse("U06", "CZ2007/01");
		sf.addCourse("U07", "CZ2007/01");
		sf.addCourse("U08", "CZ2007/01");
		sf.addCourse("U09", "CZ2007/01");
		sf.addCourse("U10", "CZ2007/01");
		sf.addCourse("U11", "CZ2007/01");
		sf.addCourse("U12", "CZ2005/03");
		sf.addCourse("U13", "CZ2002/03");
		sf.addCourse("U14", "CZ2002/01");
		Database database = new Database();
		database.setAdmindb(adminDB);
		database.setCoursedb(courseDB);
		database.setStudentdb(studentDB);

		DatabaseIO databaseio = new DatabaseIO();
		try {
			databaseio.writeData("database.ser", database);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generate timetable.
	 *
	 * @param courseTiming the course timing
	 * @return the int[][] timetable
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
}
