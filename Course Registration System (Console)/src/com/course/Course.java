package com.course;

import java.util.*;

public class Course implements java.io.Serializable {

	/** The course code. */
	private String code;

	/** The course name. */
	private String name;

	/** The list of the course's indexes. */
	private List<CourseIndex> courseIndexes = new ArrayList<>();

	/** The course's number of academic units. */
	private int AU;

	/* CONSTRUCTOR */

	/**
	 * Instantiates a new course.
	 *
	 * @param code the course code
	 * @param name the course name
	 * @param index an ArrayList of the CourseIndex object of the course
	 * @param AU the AU
	 */
	public Course(String code, String name, List<CourseIndex> index, int AU){
		this.code=code;
		this.name=name;
		this.courseIndexes=index;
		this.AU = AU;
	}

	/**
	 * Instantiates a new course.
	 *
	 * @param code the course code
	 * @param name the course name
	 * @param AU the AU
	 */
	public Course(String code, String name, int AU){
		this.code=code;
		this.name=name;
		this.AU = AU;
	}


	/**
	 * Gets the course code.
	 *
	 * @return the course code
	 */
	/* GETTERS AND SETTERS */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the course code.
	 *
	 * @param code the new course code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public List<CourseIndex> getIndex() {
		return courseIndexes;
	}

//	/* Course Functions */
//	public int getTotalSlot() {
//		int totalSlot = 0;
//		for(CourseIndex index: courseIndexes){
//			totalSlot += index.getTotalSlots();
//		}
//		return totalSlot;
//	}



	/**
 * This function returns the number of vacant slot in the course
	 * This is a sum of all the vacancy in its course indexes.
 *
 * @return the number of vacancy the course.
 */
public int vacancy(){
		int vacancy = 0;
		for (CourseIndex index: courseIndexes){
				vacancy += index.vacancy();
		}
		return vacancy;
	}

	/**
	 * Gets the number of AU.
	 *
	 * @return the AU
	 */
	public int getAU() {
		return AU;
	}

	/**
	 * Sets the AU.
	 *
	 * @param AU the new AU
	 */
	public void setAU(int AU) {
		this.AU = AU;
	}

	/**
	 * Find course index.
	 *
	 * @param code the index code
	 * @return the Course Index object
	 */
	/* courseIndexes Functions */
	public CourseIndex findCourseIndex(String code){
		for (CourseIndex index: courseIndexes){
			if(index.getCode().equals(code)){
				return index;
			}
		}
		return null;
	}

//	public boolean deleteCourseIndex(String IndexCode)
//	{
//		for(CourseIndex index: courseIndexes)
//		{
//			if(index.getCode().equals(IndexCode))
//			{
//				courseIndexes.remove(index);
//				return true;
//			}
//		}
//		return false;
//	}

	/**
 * Adds the course index.
 *
 * @param IndexCode the index code
 * @param totalSlot the total slot
 * @param timeSlot the time slot
 * @return true, if successful
 */
public boolean addCourseIndex(String IndexCode, int totalSlot, int[][] timeSlot){
		for(CourseIndex index: courseIndexes)
		{
			if(index.getCode().equals(IndexCode))
			{
				return false;
			}
		}
		CourseIndex newCourseIndex = new CourseIndex(IndexCode, totalSlot, timeSlot);
		courseIndexes.add(newCourseIndex);
		return true;
	}

//	public String[] allIndex(){
//		int size = courseIndexes.size();
//		String[] all = new String[size];
//		for(int i=0; i<size; i++){
//			all[i] = courseIndexes.get(i).getCode();
//		}
//		return all;
//	}

	/**
 * Gets the number of people in the waiting list
 *
 * @return the number of people in the waiting list
 */
public int getWaitListNumber(){
		int waiting = 0;
		for (CourseIndex courseIndex : courseIndexes) {
			waiting += courseIndex.getWaitingList().size();
		}
		return waiting;
	}

	/**
	 * Gets a list of the matricNo of the students in the course.
	 *
	 * @return a list of matricNo of the students in the course.
	 */
	public List<String> getStudents(){
		List<String> students = new ArrayList<>();
		for(CourseIndex courseIndex : courseIndexes){
			students.addAll(courseIndex.getStudents());
		}
		return students;
	}

}
