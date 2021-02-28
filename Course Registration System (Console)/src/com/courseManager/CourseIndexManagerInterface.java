package com.courseManager;

import java.util.List;

public interface CourseIndexManagerInterface {
    /**
     * This function changes the index code of an index
     * @param courseCode the course that the index corresponds to
     * @param indexCodeOld the current index code
     * @param newIndex the index code to change to
     * @return boolean: true is successful and false if unsuccessful
     */
    boolean changeIndexCode(String courseCode, String indexCodeOld, String newIndex);

    /**
     * This function changes the total slots for a given index
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param total_slots the new total slots
     * @return true is successful and false if unsuccessful
     */
    boolean changeIndexVacancy(String courseCode, String indexCode, int total_slots);


    /**
     * This function returns the vacancy of a given index
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @return the total number of vacancy of the index. null will be returned if the index code is invalid
     */
    Integer getIndexVacancy(String courseCode, String indexCode);

    /**
     * This function adds student into a course index.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return true is successful and false if unsuccessful
     */
    boolean addStudent(String courseCode, String indexCode, String matricNo);


    /**
     * * This function removes a student for a course index.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code of the course
     * @param matricNo the matriculation number of the student
     * @return boolean: true is successful and false if unsuccessful

     */
    boolean removeStudent(String courseCode, String indexCode, String matricNo);

    /**
     * This function registers a student for the waiting list.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return boolean: returns true if the student is added to the course index and false if the student is added into the waiting list.

     */
    Integer register(String courseCode, String indexCode, String matricNo);

    /**
     * This function unregisters a student from a given course.
     * If there are students in the waiting list, this student will be registered into the index.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return returns the matriculation number of the student that is at the top of the waiting list and null if there are none.
     */
    String unregister(String courseCode, String indexCode, String matricNo);

    /**
     * Gets the number of people in the waiting list of the index.
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the index waiting number
     */
    Integer getIndexWaitingNumber(String courseCode, String indexCode);

    /**
     * Gets the time slot of the course index.
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the time slot
     */
    int[][] getTimeSlot(String courseCode, String indexCode);

    /**
     * Gets the total slot of the course index
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the total slot of the course index.
     */
    Integer getTotalSlot(String courseCode, String indexCode);

    /**
     * Change time slot of the course index
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @param timeSlot the time slot
     * @return true, if successful
     */
    boolean changeTimeSlot(String courseCode, String indexCode, int[][] timeSlot);

    /**
     * Gets the list of students that are in the course index.
     * @param courseCode the course code
     * @param indexCode the index code
     * @return an list array that contains the matriculation number of all students in the course index
     */
    List<String> getIndexStudentList(String courseCode,String indexCode);

    /**
     * Swop course index.
     *
     * @param student1 the matriculation number of student 1
     * @param student2 the matriculation number of student 2
     * @param courseCode the course course
     * @param indexCode1 the index code 1
     * @param indexCode2 the index code 2
     * @return true, if successful
     */
    boolean swopCourseIndex(String student1, String student2, String courseCode, String indexCode1, String indexCode2);






}
