package com.courseManager;

import com.course.*;
import com.database.CourseCodeExistsException;
import com.database.CourseDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseManager implements  CourseManagerInterface, CourseIndexManagerInterface{
    /** The course database. */
    private final CourseDB courseDatabase;

    /** The course. */
    private Course course;

    /** The course index. */
    private CourseIndex courseIndex;


    /**
     * Sets the course.
     *
     * @param CourseCode the course code
     * @return true, if successful
     */
    private boolean setCourse(String CourseCode){
        if(course == null){
            course = courseDatabase.findCourse(CourseCode);
            return course != null;
        }
        else if(!course.getCode().equals(CourseCode)) {
            Course temp = courseDatabase.findCourse(CourseCode);
            if (temp == null) {
//                System.out.print("Course does not exists");
                return false;
            }
            course = temp;
        }
        return true;
    }

    /**
     * Sets the course index.
     *
     * @param indexCode the index code
     * @return true, if successful
     */
    private boolean setCourseIndex(String indexCode){
        if(courseIndex == null){
            courseIndex = course.findCourseIndex(indexCode);
            return courseIndex != null;
        }
        else if(!courseIndex.getCode().equals(indexCode)) {
            CourseIndex temp2 = course.findCourseIndex(indexCode);
            if (temp2 == null) {
                System.out.print("Course index does not exists");
                return false;
            }
            courseIndex = temp2;
        }
        return true;
    }

    /**
     * Constructor for the CourseManager
     * @param courseDatabase the course database that the Manager will be managing
     */
    public CourseManager(CourseDB courseDatabase) {
        this.courseDatabase = courseDatabase;
    }



    /* Course functions */

    /**
     * Gets all the courses in the database
     * @return list array of string that contains the code of all courses
     */
    public List<String> getAllCourses(){
        return courseDatabase.getCourseStringList();
    }

    /**
     * Gets the course vacancy.
     *
     * @param courseCode the course code
     * @return the course vacancy
     * Returns null if the course code is invalid
     */
    public Integer getCourseVacancy(String courseCode){
        if(!(setCourse(courseCode))){return null;}
        return this.course.vacancy();
    }

    /**
     * Gets the number of people currently in the waiting list of the course
     *
     * @param courseCode the course code
     * @return the number of people currently in the waiting list
     * Returns null if the course code is invalid
     */
    public Integer getCourseWaitingNumber(String courseCode){
        if(!(setCourse(courseCode))){return null;}
        return this.course.getWaitListNumber();
    }

    /**
     * Creates the new course.
     *
     * @param code the course code
     * @param name the name of the course
     * @param AU the number of AU the course has
     * @return true, if successful
     * @throws CourseCodeExistsException course already exists
     */
    public boolean createNewCourse(String code, String name, int AU) throws CourseCodeExistsException {
        return courseDatabase.addCourse(code, name, AU);
    }

    /**

     * This function creates a new course index
     * @param course_code the course code that the index corresponds to
     * @param indexCode the new index code
     * @param totalSlot the total number of slots the index has
     * @param timeSlot the time slots of the index's lessons
     * @return boolean: true is successful and false if unsuccessful
     */
    public boolean createNewCourseIndex(String course_code,String indexCode, int totalSlot, int[][] timeSlot){
        if(!(setCourse(course_code))){return false;}
        return this.course.addCourseIndex(indexCode, totalSlot, timeSlot);
    }

    /**
     * This function changes a course code
     * @param old the old course code
     * @param newCode the new course code
     * @return boolean: true is successful and false if unsuccessful
     */
    public boolean changeCourseCode(String old, String newCode) {
        if(!(setCourse(old))){return false;}
        if (this.courseDatabase.findCourse(newCode) == null){
            this.course.setCode(newCode);
            return true;
        }else{
            System.out.println("Course code already exists");
            return false;
        }
    }

    /**
     * This function returns the list of students in a given course
     * @param courseCode the course code
     * @return the list of students in given course
     */
    public List<String> getCourseStudentList(String courseCode){
        if(!(setCourse(courseCode))){return null;}
        return this.course.getStudents();
    }

    /**
     * Gets the course AU.
     *
     * @param courseCode the course code
     * @return the course AU
     */
    /* CourseAU getter and setter */
    public int getCourseAU(String courseCode){
        if(courseDatabase.findCourse(courseCode)!=null){
            Course courseToFind = courseDatabase.findCourse(courseCode);
            return courseToFind.getAU();
        }
        return -1;
    }

    /**
     * Sets the course AU.
     *
     * @param courseCode the course code
     * @param AUToSet the AU to set
     * @return true, if successful
     */
    public boolean setCourseAU(String courseCode,int AUToSet){
        if(courseDatabase.findCourse(courseCode)!=null){
            Course courseToFind = courseDatabase.findCourse(courseCode);
            courseToFind.setAU(AUToSet);
            return true;
        }
        return false;
    }






    /* CourseIndex functions */

    /**
     * This function changes the index code of an index
     * @param courseCode the course that the index corresponds to
     * @param indexCodeOld the current index code
     * @param newIndex the index code to change to
     * @return boolean: true is successful and false if unsuccessful
     */
    public boolean changeIndexCode(String courseCode, String indexCodeOld, String newIndex) {
        if(!(setCourse(courseCode) && setCourseIndex(indexCodeOld))) {return false;}
        if (this.course.findCourseIndex(newIndex) == null){
            this.courseIndex.setCode(newIndex);
            return true;
        }else{
            System.out.println("Index code already exits");
            return false;
        }
    }

    /**
     * This function changes the total slots for a given index
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param total_slots the new total slots
     * @return true is successful and false if unsuccessful
     */
    public boolean changeIndexVacancy(String courseCode, String indexCode, int total_slots) {
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return false;}
        if (this.courseIndex != null){
            this.courseIndex.setTotalSlots(total_slots);
            return true;
        }else{
            return false;
        }
    }

    /**
     * This function returns the vacancy of a given index
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @return the total number of vacancy of the index. null will be returned if the index code is invalid
     */
    public Integer getIndexVacancy(String courseCode, String indexCode){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        return this.courseIndex.vacancy();
    }

    /**
     * This function adds student into a course index.
     * If the course has no vacancy, no further actions will be taken.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return true is successful and false if unsuccessful
     */
    public boolean addStudent(String courseCode, String indexCode, String matricNo) {
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return false;}
        return this.courseIndex.addStudent(matricNo);
    }

    /**

     * This function removes a student for a course index.
     * No further actions are taken even if there are students in the waiting list
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code of the course
     * @param matricNo the matriculation number of the student
     * @return boolean: true is successful and false if unsuccessful

     */
    public boolean removeStudent(String courseCode, String indexCode, String matricNo) {
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return false;}
        return this.courseIndex.removeStudent(matricNo);
    }

    /**
     * This function registers a student for the waiting list.
     * If there are vacant slots, the student will be added into the course instead.
     * -1 - courseCode or courseIndexCode is invalid
     * 0 - student is added to the waiting list
     * 1 - student successfully registered for the course
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return boolean: returns true if the student is added to the course index and false if the student is added into the waiting list.

     */
    //1 - added to courseIndex. 0 - added to waitList
    public Integer register(String courseCode, String indexCode, String matricNo){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return -1;}
        if (this.courseIndex.vacancy() != 0) {
            this.courseIndex.addStudent(matricNo);
            return 1;
        } else {
            this.courseIndex.addWaitingList(matricNo);
//            System.out.print("Student is added to waiting list");
            return 0;
        }
    }

    /**
     * This function unregisters a student from a given course.
     * If there are students in the waiting list, this student will be registered into the index.
     * @param courseCode the course that the index corresponds to
     * @param indexCode the index code
     * @param matricNo the matriculation number of the student
     * @return returns the matriculation number of the student that is at the top of the waiting list and null if there are none.
     */
    public String unregister(String courseCode, String indexCode, String matricNo) {
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        if (this.courseIndex.removeStudent(matricNo)) {
            if (this.courseIndex.getWaitingNumber() != 0) {
                // New Student Registered
                return this.courseIndex.dequeueAndFill();
            }
        } else {
            this.courseIndex.removeWaitingList(matricNo);
        }
        return null;
    }


    /**
     * Gets the number of people in the waiting list of the index
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the index waiting number
     */
    public Integer getIndexWaitingNumber(String courseCode, String indexCode){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        return this.courseIndex.getWaitingNumber();
    }

    /**
     * Gets the time slot of the course index.
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the time slot
     */
    public int[][] getTimeSlot(String courseCode, String indexCode){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        return this.courseIndex.getTimeSlot();
    }

    /**
     * Gets the total slot of the course index
     * @param courseCode the course code
     * @param indexCode the index code
     * @return the total slot of the course index. null if index code is invalid
     */
    public Integer getTotalSlot(String courseCode, String indexCode){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        return this.courseIndex.getTotalSlots();
    }

    /**
     * Change time slot of the course index
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @param timeSlot the time slot
     * @return true, if successful
     */
    public boolean changeTimeSlot(String courseCode, String indexCode, int[][] timeSlot){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return false;}
        for(CourseIndex index: this.course.getIndex()){
            if(this.courseIndex.getCode().equals(this.course.getCode())){
                continue;
            }
            if(Arrays.deepEquals(index.getTimeSlot(), timeSlot))
            return false;
        }
        this.courseIndex.setTimeSlot(timeSlot);
        return true;
    }


    /**
     * Gets the list of students that are in the course index.
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @return an list array that contains the matriculation number of all students in the course index
     */
    public List<String> getIndexStudentList(String courseCode,String indexCode){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode))) {return null;}
        return this.courseIndex.getStudents();
    }

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
    public boolean swopCourseIndex(String student1, String student2, String courseCode, String indexCode1, String indexCode2){
        if(!(setCourse(courseCode) && setCourseIndex(indexCode1) && setCourseIndex(indexCode2))){return false;}
        this.removeStudent(courseCode, indexCode1, student1);
        this.addStudent(courseCode, indexCode1, student2);
        this.removeStudent(courseCode, indexCode2, student2);
        this.addStudent(courseCode, indexCode2, student1);
        return true;
    }

    /**
     * Check course exists.
     *
     * @param courseCode the course code
     * @return true, if successful
     */
    public boolean checkCourseExists(String courseCode){
        return (this.setCourse(courseCode));
    }

    /**
     * Check index exists.
     *
     * @param courseCode the course code
     * @param indexCode the index code
     * @return true, if successful
     */
    public boolean checkIndexExists(String courseCode, String indexCode){
        return (this.setCourse(courseCode) && this.setCourseIndex(indexCode));
    }

    /**
     * Get all the index available for the course
     * @param courseCode the course code
     * @return a list array of the course's index code
     */
    public List<String> getAllIndex(String courseCode){
        if(!(setCourse(courseCode))){return null;}
        List<String> allIndex = new ArrayList<>();
        for(CourseIndex index:course.getIndex()){
            allIndex.add(index.getCode());
        }
        return allIndex;
    }
}
