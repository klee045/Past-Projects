package com.course;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseIndex implements java.io.Serializable {

	/** The course index code. */
	private String code;

	/** The total slot. */
	private int totalSlot;

	/** The matric no. of students registered for the course. */
	private List<String> students = new ArrayList<String>();

	/** The matric no. of student in course index waiting list. */
	private Queue<String> waitingList = new LinkedList<String>();

	/** The time slot of the course index */
	private int[][] timeSlot;

	/**
	 * Instantiates a new course index.
	 *
	 * @param code 		the index code
	 * @param totalSlot the total slot
	 * @param timeSlot  the time slot
	 */
	/* CONSTRUCTOR */
	public CourseIndex(String code, int totalSlot, int[][] timeSlot) {
		this.code = code;
		this.totalSlot = totalSlot;
		this.timeSlot = timeSlot;
	}

	/**
	 * Gets the total slots.
	 *
	 * @return the total slots
	 */
	/* GETTERS AND SETTERS */
	public int getTotalSlots() {
		return totalSlot;
	}

	/**
	 * Sets the total slots.
	 *
	 * @param totalSlots the new total slots
	 */
	public void setTotalSlots(int totalSlots) {
		this.totalSlot = totalSlots;
	}

	/**
	 * Gets the index code.
	 *
	 * @return the index code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the index code.
	 *
	 * @param code the new index code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the matric no. of students that are registered for the course index.
	 *
	 * @return a list array containing the matriculation number of the students in the course index
	 */
	public List<String> getStudents() {
		return students;
	}

	/**
	 * Gets the time slot.
	 *
	 * @return the time slot
	 */
	public int[][] getTimeSlot() {
		return timeSlot;
	}

	/**
	 * Sets the time slot.
	 *
	 * @param timeSlot the new time slot
	 */
	public void setTimeSlot(int[][] timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * Gets the matric no. of students in the index waiting list.
	 *
	 * @return the waiting list
	 */
	public List<String> getWaitingList() {
		List<String> output = new ArrayList<String>();
		for (String matricNo : waitingList) {
			output.add(matricNo);
		}
		return output;
	}

	/**
	 * Gets the number of students in the waiting list
	 *
	 * @return the the number of people in the waiting list
	 */
	public int getWaitingNumber() {
		return this.getWaitingList().size();
	}

	/**
	 * Gets the number of vacant slots in the course index
	 *
	 * @return the number of vacant slots in the course index
	 */
	/* Other Functions */
	public int vacancy() {
//		System.out.println("vacancy (debugging): " + totalSlot + "-" + students.size());
		return totalSlot - students.size();
	}

	/**
	 * Adds the student into the course index, provided there are vacancy in the course index.
	 * @param matricNo the matric no. of the student to be added to course index.
	 * @return true, if successful
	 */
	public boolean addStudent(String matricNo) {
		if (matricNo == null) {
			return false;
		}
		if (vacancy() != 0) {
			for (String student : students) {
				if (student.equals(matricNo)) {
					return false;
				}
			}
			students.add(matricNo);
//			System.out.println("Index returning true");
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Removes the student from the course index
	 *
	 * @param s the matriculation number of the student
	 * @return true, if successful
	 */
	public boolean removeStudent(String s) {
		if (s == null) {
			return false;
		}
		for (String student : students) {
			if (student.equals(s)) {
				students.remove(student);
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the student to the waiting list of the course index
	 *
	 * @param s the matriculation number of the student
	 * @return true, if successful
	 */
	public boolean addWaitingList(String s) {
		for (String student : waitingList) {
			if (student.equals(s)) {
				return false;
			}
		}
		waitingList.add(s);
		return true;
	}

	/**
	 * Removes the student from the waiting list
	 *
	 * @param matricNo the matric no. of the student
	 * @return true, if successful
	 */
	public boolean removeWaitingList(String matricNo) {
		for (String student : waitingList) {
			if (student.equals(matricNo)) {
				waitingList.remove(student);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove the student at the front of the waiting list and remove him from the waiting list
	 * @return the matriculation number of student at the front of the waiting list
	 */
	private String dequeue() {
		return waitingList.poll();
	}

	/**
	 * Dequeue from the waiting list and add student into the course index.
	 *
	 * @return the matricNumber of the student added into the course index.
	 */
	public String dequeueAndFill() {
		String student = this.dequeue();
		if (student == null) {
			return null;
		} else if (this.addStudent(student)) {
			return student;
		}
		return null;
	}

}
