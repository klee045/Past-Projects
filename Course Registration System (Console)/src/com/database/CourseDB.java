package com.database;

import com.course.Course;
import com.course.CourseIndex;

import java.util.*;

public class CourseDB implements java.io.Serializable{

	/** Arraylist that stores course object. */
	private List<Course> courseList = new ArrayList<Course>();

	/**
	 * Instantiates a new course DB.
	 *
	 * @param courseList the course list
	 */
	public CourseDB(List<Course> courseList)
	{
		this.courseList= courseList;
	}

	/**
	 * Add the course object into the courseList.
	 *
	 * @param code Course code
	 * @param name Course name
	 * @param AU Course AU
	 * @throws CourseCodeExistsException error is thrown when course code already exists
	 * @return true, if successful
	 * @throws CourseCodeExistsException course already exists
	 */
	public boolean addCourse(String code, String name, int AU) throws CourseCodeExistsException
	{
		for(Course c: courseList){
			if(c.getCode().equalsIgnoreCase(code)){
				throw new CourseCodeExistsException("Course Code already exists");
			}
		}
		List<CourseIndex> courseIndexes = new ArrayList<CourseIndex>();
		Course newCourse = new Course(code, name, courseIndexes, AU);
		courseList.add(newCourse);
		return true;
	}


	/**
	 * Get course.
	 *
	 * @param courseCode Course code
	 * @return the course object
	 */
	public Course findCourse(String courseCode)
	{
		for(Course c:courseList)
		{
			if(c.getCode().equalsIgnoreCase(courseCode))
			{
				return c;
			}
		}
		return null;
	}

	/**
	 * Remove the course object from the courseList
	 *
	 * @param courseCode Course code
	 * @return true, if successful
	 */
	public boolean deleteCourse(String courseCode)
	{
		for(Course c:courseList)
		{
			if(c.getCode().equalsIgnoreCase(courseCode))
			{
				courseList.remove(c);
				return true;
			}
		}
		return false;
	}


	/**
	 * Get all the course names
	 *
	 * @return the string[] all the course names
	 */
	public String[] allName(){
		int size = courseList.size();
		String[] all = new String[size];
		for(int i=0; i<size; i++){
			all[i] = courseList.get(i).getName();
		}
		return all;
	}

	/**
	 * Return all the course codes
	 *
	 * @return the string[] all the course codes
	 */
	public String[] courseCode(){
		int size = courseList.size();
		String[] all = new String[size];
		for(int i=0; i<size; i++){
			all[i] = courseList.get(i).getCode();
		}
		return all;
	}

	/**
	 * Get the vacancy of all courses.
	 *
	 * @return the int[] the vacancy of all courses
	 */
	public int[] vacancy(){
		int size = courseList.size();
		int[] all = new int[size];
		for(int i=0; i<size; i++){
			all[i] = courseList.get(i).vacancy();
		}
		return all;
	}

	/**
	 * Get the number of waitlist of all the courses.
	 *
	 * @return the int[] number of waitlist of all the courses
	 */
	public int[] waitList(){
		int size = courseList.size();
		int[] all = new int[size];
		for(int i=0; i<size; i++){
			all[i] = courseList.get(i).getWaitListNumber();
		}
		return all;
	}

	/**
	 * Get the course list.
	 *
	 * @return the course list
	 */
	// testing function
	public List<Course> getCourseList() {
		return courseList;
	}

	public List<String> getCourseStringList(){
		List<String> output = new ArrayList<>();
		for(Course course: courseList){
			output.add(course.getCode());
		}
		return output;
	}

}
