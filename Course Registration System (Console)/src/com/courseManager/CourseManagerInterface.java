package com.courseManager;

import com.database.CourseCodeExistsException;

import java.util.List;

public interface CourseManagerInterface {

    /**
     * Creates the new course.
     *
     * @param code the course code
     * @param name the name of the course
     * @param AU the number of AU the course has
     * @throws CourseCodeExistsException throws error when course code already exists
     * @return true, if successful
     */
     boolean createNewCourse(String code, String name, int AU) throws CourseCodeExistsException;


    /**
     * Gets the course vacancy.
     *
     * @param courseCode the course code
     * @return the course vacancy
     * Returns null if the course code is invalid
     */
    Integer getCourseVacancy(String courseCode);


    /**
     * Gets the number of people currently in the waiting list of the course
     *
     * @param courseCode the course code
     * @return the number of people currently in the waiting list
     * Returns null if the course code is invalid
     */
    Integer getCourseWaitingNumber(String courseCode);


    /**
     * This function changes a course code
     * @param old the old course code
     * @param newCode the new course code
     * @return boolean: true is successful and false if unsuccessful
     */
    boolean changeCourseCode(String old, String newCode);

    /**
     * This function returns the list of students in a given course
     * @param courseCode the course code
     * @return the list of students in given course
     */
    List<String> getCourseStudentList(String courseCode);

    /**
     * Gets the course AU.
     *
     * @param courseCode the course code
     * @return the course AU
     */
    /* CourseAU getter and setter */
    int getCourseAU(String courseCode);

    /**
     * Sets the course AU.
     *
     * @param courseCode the course code
     * @param AUToSet the AU to set
     * @return true, if successful
     */
    boolean setCourseAU(String courseCode,int AUToSet);

    /**
     * Get all the index available for the course
     * @param courseCode the course code
     * @return a list array of the course's index code
     */
    List<String> getAllIndex(String courseCode);


}
