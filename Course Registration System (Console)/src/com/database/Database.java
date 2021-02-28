package com.database;

public class Database implements java.io.Serializable{

	/** The studentdb student database. */
	/* Database that stores student data */
	private StudentDB studentdb;

	/** The coursedb course database - stores courses and course index. */
	private CourseDB coursedb;

	/** The admindb admin database. */
	private AdminDB admindb;


	/**
	 * Gets the admindb.
	 *
	 * @return the admindb
	 */
	public AdminDB getAdmindb() {
		return admindb;
	}

	/**
	 * Gets the coursedb.
	 *
	 * @return the coursedb
	 */
	public CourseDB getCoursedb() {
		return coursedb;
	}

	/**
	 * Gets the studentdb.
	 *
	 * @return the studentdb
	 */
	public StudentDB getStudentdb() {
		return studentdb;
	}

	/**
	 * Sets the admindb.
	 *
	 * @param admindb the new admindb
	 */
	public void setAdmindb(AdminDB admindb) {
		this.admindb = admindb;
	}

	/**
	 * Sets the coursedb.
	 *
	 * @param coursedb the new coursedb
	 */
	public void setCoursedb(CourseDB coursedb) {
		this.coursedb = coursedb;
	}

	/**
	 * Sets the studentdb.
	 *
	 * @param studentdb the new studentdb
	 */
	public void setStudentdb(StudentDB studentdb) {
		this.studentdb = studentdb;
	}
}
