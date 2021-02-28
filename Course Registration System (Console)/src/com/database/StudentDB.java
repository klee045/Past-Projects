package com.database;

import com.users.Student;

import java.util.*;

public class StudentDB implements java.io.Serializable{

	/** The student list. */
	private List<Student> studentList = new ArrayList<Student>();

	/**
	 * Instantiates a new student DB.
	 *
	 * @param studentList the student list
	 */
	public StudentDB(List<Student> studentList)
	{
		this.studentList=studentList;
	}

	/**
	 * Gets the student list.
	 *
	 * @return the student list
	 */
	public List<Student> getStudentList()
	{
		return studentList;
	}

	/**
	 * Add new student to the student list.
	 *
	 * @param s Student object to be added
	 */
	public void addStudent(Student s)
	{
		studentList.add(s);
	}

	/**
	 * Find the particular student object by using the matricNo.
	 *
	 * @param matricNo Student's matric numebr
	 * @return the student object
	 */
	public Student findStudent(String matricNo)
	{
		for(Student s:studentList)
		{
			if(s.getMatricNo().equals(matricNo))
			{
				return s;
			}
		}
		return null;
	}


	/**
	 * Remove the student object from the courseList
	 *
	 * @param username Student's username
	 * @return the student object
	 */

	public Student findStudentByUsername(String username)
	{
		for(Student s: studentList)
		{
			if (s.getUsername().equalsIgnoreCase(username))
			{
				return s;
			}
		}
		return null;
	}

	/**
	 * Remove the student object from the studentList
	 *
	 * @param matricNo the matric number
	 * @return true, if successful
	 */

	public boolean deleteStudent(String matricNo)
	{
		for(Student s:studentList)
		{
			if(s.getMatricNo().equals(matricNo))
			{
				studentList.remove(s);
				return true;
			}
		}
		return false;
	}


}
