package com.functions;

import com.courseManager.CourseManager;
import com.database.CourseCodeExistsException;
import com.studentManager.Studentmgr;

public class AdminFunctions {

    /** The course manager. */
    private CourseManager courseManager;

    /** The student manager. */
    private Studentmgr studentManager;

    /**
     * Constructor for AdminFunctions
     *
     * @param courseManager controller for course-related functions
     * @param studentManager controller for student-related functions
     */
    public AdminFunctions(CourseManager courseManager, Studentmgr studentManager) {
        this.courseManager = courseManager;
        this.studentManager = studentManager;
    }

    /**
     * Edits the access period of student.
     *
     * @param inStudent the student
     * @param AccPerStart the acc per start
     * @param AccPerEnd the acc per end
     * @return true, if successful
     */
    public boolean editAccessPeriod(String inStudent, String AccPerStart,String AccPerEnd) {
        return studentManager.updateAccPerStart(inStudent,AccPerStart) && studentManager.updateAccPerEnd(inStudent,AccPerEnd);
    }


    /**
     * Adds a student; and
     * Prints all students after adding
     *
     * @param username the username
     * @param password the password
     * @param accessPeriodStart the access period start
     * @param accessPeriodEnd the access period end
     * @param name the student name
     * @param gender the gender
     * @param nationality the nationality
     * @param school the school
     * @param email the email address of student
     */

    public void addStudent(String username, String password, String accessPeriodStart, String accessPeriodEnd,String name,String gender,String nationality,String school, String email) {
        studentManager.addStudent(username, password, accessPeriodStart,accessPeriodEnd,name, gender,nationality, school, email);
        studentManager.getAllStudents();
        System.out.println();
    }

    /**
     * Adds the new course; and
     * prints all courses with their vacancies
     * newly-added courses should have 0 vacancy until indexes are added
     *
     * @param code the course code
     * @param name the name of the course
     * @param AU the no. of AUs for the course
     * @throws CourseCodeExistsException error is thrown when course code already exists
     */
    public void addNewCourse(String code, String name, int AU) throws CourseCodeExistsException {
        courseManager.createNewCourse(code, name, AU);
        System.out.println("After adding, all courses are:");
        System.out.println("Course Code, Vacancy");
        for (String course : courseManager.getAllCourses()) {
            System.out.print(course);
            System.out.print(",\t" + courseManager.getCourseVacancy(course));
            System.out.println();
        }
    }

    /**
     * Adds a new index to a course.
     *
     * @param code course code
     * @param indexCode course index
     * @param totalSlot no. of vacancies
     * @param timeSlot time slot when the class would be
     */
    public void addNewCourseIndex(String code, String indexCode, int totalSlot, int[][] timeSlot) {
        courseManager.createNewCourseIndex(code, indexCode, totalSlot, timeSlot);
    }

    /**
     * Update course code.
     *
     * @param old the old course code
     * @param newCode the new course code
     * @return new course code if successful and old course code if unsuccessful
     */
    public String updateCourseCode(String old,String newCode) {
        String oldIdx, newIdx;
        try {
            for (String student : courseManager.getCourseStudentList(old)) {
                oldIdx = old + "/" + studentManager.findStudentCourseIndex(student, old);
                newIdx = newCode + "/" + studentManager.findStudentCourseIndex(student, old);
                studentManager.changeIndex(student, oldIdx, newIdx);
            }
        } catch (NullPointerException e) {
            System.out.println("Course does not exist");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        if(courseManager.changeCourseCode(old, newCode)){
            return newCode;
        };
        return old;
    }


    /**
     * Update course index.
     * Change index of each student under the old course index first
     * Change the index of the course after that
     * If course code or course index does not exist, null is returned which will be caught
     *
     * @param course_code the intended course code
     * @param old the old course index
     * @param newIndex the new course index
     */
    public void updateCourseIndex(String course_code, String old, String newIndex) {
        String oldIdx = course_code + "/" + old;
        String newIdx = course_code + "/" + newIndex;
        try {
            for (String student : courseManager.getIndexStudentList(course_code, old)) {
                if(student != null){
                    studentManager.changeIndex(student, oldIdx, newIdx);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Course/Index does not exist??");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        courseManager.changeIndexCode(course_code, old, newIndex);
    }

    /**
     * Update course vacancy.
     *
     * @param course_code indicate the course
     * @param index the targeted index to update
     * @param total_slots the total slots to update to
     */
    public void updateCourseVacancy(String course_code,String index,int total_slots) {
        courseManager.changeIndexVacancy(course_code,index,total_slots);
    }


    /**
     * Prints the student list by course index.
     *
     * @param course_code the course code
     * @param course_index the course index
     */
    public void printStudentListByIndex(String course_code, String course_index) {
        try {
            System.out.println("Students enrolled:");
            for (String s : courseManager.getIndexStudentList(course_code, course_index)) {
                studentManager.printStudentInfo(s);
            }
        } catch (NullPointerException e) {
            System.out.println("Course/Index does not exist");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prints the student list by course.
     *
     * @param course_code the course code
     */
    public void printStudentListByCourse(String course_code) {
        try {
            System.out.println("Students enrolled:");
            for (String s : courseManager.getCourseStudentList(course_code)) {
                if(s != null){
                    studentManager.printStudentInfo(s);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Course does not exist");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Check class vacancy.
     *
     * @param course_code the course code
     * @param course_index the course index
     * @return the vacancy
     */
    public int checkClassVacancy(String course_code, String course_index) {
        if (courseManager.getIndexVacancy(course_code, course_index) == null) {
            System.out.println("Course/Index does not exist.");
            return -1;
        }
        return courseManager.getIndexVacancy(course_code, course_index);
    }

    public boolean checkIndexExists(String course_code, String course_index){
        return courseManager.checkIndexExists(course_code, course_index);
    }
}

