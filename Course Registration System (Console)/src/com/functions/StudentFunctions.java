package com.functions;

import com.courseManager.CourseManager;
import com.notificationFactory.NotificationFactory;
import com.studentManager.Studentmgr;
import com.users.Student;

import java.util.List;

public class StudentFunctions {

    /** The Course manager. */
    private com.courseManager.CourseManager CourseManager;

    /** The Studentmgr. */
    private com.studentManager.Studentmgr Studentmgr;


    // courseCodeIndex looks like CZ2002/01
    // CourseCode looks like CZ2002
    //courseCodeIndex_last2 looks like 01

    /**
     * Instantiates a new student functions.
     *
     * @param CourseManager the course manager
     * @param Studentmgr the studentmgr
     */
    public StudentFunctions(CourseManager CourseManager, Studentmgr Studentmgr){
        this.CourseManager = CourseManager;
        this.Studentmgr = Studentmgr;
    }

    /**
     * Gets the au.
     *
     * @param student_matric the student matric
     * @return the au
     */
    public int getAU(String student_matric){
        return Studentmgr.getAU(student_matric);
    }


    /**
     * Check timetable.
     *
     * @param student_matric the student matric
     * @param courseCodeIndex the course code index
     * @return true, if successful
     */
    public boolean checkTimetable(String student_matric, String courseCodeIndex){
        String courseCode = courseCodeIndex.substring(0,6);
        String courseCodeIndex_last2 = courseCodeIndex.substring(7,9);
        int[][] courseTimetable = CourseManager.getTimeSlot(courseCode,courseCodeIndex_last2);
        int[][] studentTimetable = Studentmgr.getStudentTimetable(student_matric);
        boolean will_not_clash = true;
        for(int session=0;session<48;session++){
            for(int day=0;day<7;day++){
            	if(courseTimetable[session][day]==1 && studentTimetable[session][day]==1){
                    will_not_clash = false;
                    break;
                }
            }
        }
        return will_not_clash;
    }

    /**
     * Adds the course.
     *
     * @param student_matric the student matric
     * @param courseCodeIndex the course code index
     * @return the int
     */
    public int addCourse(String student_matric,String courseCodeIndex){
        // return 1 - Successfully added
        // return 2 -  Added to waiting list/No vacancies left
        // return 3 - Timetable clash or invalid AU
        // return 4 - Course Index does not exist
        // return 5 - AU limit exceeded
        // return 6 - Student already taking course
        // return 7 - Student already in wait list for the course
        String courseCode = courseCodeIndex.substring(0,6);
        String courseCodeIndex_last2 = courseCodeIndex.substring(7,9);
        int courseAU = CourseManager.getCourseAU(courseCode);
        // check course exist
        if(CourseManager.checkIndexExists(courseCode,courseCodeIndex_last2)){

            if(Studentmgr.checkCourse(student_matric,courseCodeIndex)){
                return 6;
            }
            if(Studentmgr.checkWaitlist(student_matric,courseCodeIndex)){
                return 7;
            }
            if(!Studentmgr.checkAU(student_matric,courseAU)){
                return 5;
            }
            // check timetable clash
            if(checkTimetable(student_matric,courseCodeIndex)){
                int[][] courseTimetable = CourseManager.getTimeSlot(courseCode,courseCodeIndex_last2);
                if(CourseManager.addStudent(courseCode,courseCodeIndex_last2,student_matric)) {


                    Studentmgr.addCourse(student_matric,courseCodeIndex);
                    Studentmgr.updateTimetable(student_matric,1,courseTimetable);
                    Studentmgr.updateAU(student_matric,courseAU,1);
                    return 1;
                }
                else{
                    CourseManager.register(courseCode,courseCodeIndex_last2,student_matric);
                    Studentmgr.updateWaitlist(student_matric,courseCodeIndex,1);
                    return 2;
                }
            }
            return 3;
        }

        return 4;
    }


    /**
     * Drop course.
     *
     * @param student_matric the student matric
     * @param courseCodeIndex the course code index
     * @throws Exception an exception is thrown if there is an error with the sending of notification
     * @return true, if successful
     */
    public boolean dropCourse(String student_matric, String courseCodeIndex) throws Exception {
        String courseCode = courseCodeIndex.substring(0,6);
        String courseCodeIndex_last2 = courseCodeIndex.substring(7,9);
        int courseAU = CourseManager.getCourseAU(courseCode);
        if(CourseManager.checkIndexExists(courseCode,courseCodeIndex_last2)){
            int[][] courseTimetable = CourseManager.getTimeSlot(courseCode,courseCodeIndex_last2);
            if(Studentmgr.dropCourse(student_matric,courseCodeIndex)){
                String studentToMail = CourseManager.unregister(courseCode,courseCodeIndex_last2,student_matric);
                Studentmgr.addCourse(studentToMail, courseCodeIndex);
                if(studentToMail!=null){
                    String emailToMail = Studentmgr.getEmail(studentToMail);
                    String messageType = Studentmgr.getMessageType(studentToMail);
                    Studentmgr.updateWaitlist(studentToMail,courseCodeIndex,0);
                    Studentmgr.updateTimetable(studentToMail,1,courseTimetable);
                    NotificationFactory.sendMessage(messageType,emailToMail,"./src/com/notification/outOfWaitlist.txt",courseCodeIndex);
                    System.out.println("Email sent");
                }

                Studentmgr.updateTimetable(student_matric,0,courseTimetable);
                Studentmgr.updateAU(student_matric,courseAU,0);

                return true;
            }

            return false;
        }

        return false;
    }



    /**
     * Swop index.
     *
     * @param student1 the student 1 matriculation number
     * @param student2 the student 2 matriculation number
     * @param student1_course the student 1 index code
     * @param student2_course the student 2 index code
     * @return true, if successful
     */
    public boolean swopIndex(String student1,String student2,String student1_course,String student2_course){
        String courseCode = student1_course.substring(0,6);
        String studentCourseCodeIndex1_last2 = student1_course.substring(7,9);
        String studentCourseCodeIndex2_last2 = student2_course.substring(7,9);
        if(!courseCode.equals(student2_course.substring(0,6))){
        	System.out.println("Courses swapped cannot be different");
            return false;
        }
        if(student1_course.equals(student2_course)){
            System.out.println("Courses are identical!");
            return false;
        }
        if(student1.equals(student2)){
        	System.out.println("Pleases enter different student");
            return false;
        }
        if(CourseManager.checkIndexExists(courseCode,studentCourseCodeIndex1_last2)&&CourseManager.checkIndexExists(courseCode,studentCourseCodeIndex2_last2)){
            // drop timetable 1 for student 1
            int[][] courseTimetable1 = CourseManager.getTimeSlot(courseCode,studentCourseCodeIndex1_last2);
            Studentmgr.updateTimetable(student1,0,courseTimetable1);
            // drop timetable 2 for student 2
            int[][] courseTimetable2 = CourseManager.getTimeSlot(courseCode,studentCourseCodeIndex2_last2);
            Studentmgr.updateTimetable(student2,0,courseTimetable2);
            // if no clash
            if ((checkTimetable(student1,student2_course))&&(checkTimetable(student2,student1_course))){
                if(Studentmgr.swopIndex(student1,student2,student1_course,student2_course)){
                    CourseManager.swopCourseIndex(student1,student2,courseCode,studentCourseCodeIndex1_last2,studentCourseCodeIndex2_last2);
                    Studentmgr.updateTimetable(student1,1,courseTimetable2);
                    Studentmgr.updateTimetable(student2,1,courseTimetable1);


                    return true;
                }
                System.out.println("Student does not take that course");
                Studentmgr.updateTimetable(student1,1,courseTimetable1);
                Studentmgr.updateTimetable(student2,1,courseTimetable2);
                return false;
            }
            // if clash add original timetable back
            else{
                System.out.println("There are timetable clashes");
                Studentmgr.updateTimetable(student1,1,courseTimetable1);
                Studentmgr.updateTimetable(student2,1,courseTimetable2);
            }

            return false;
        }
        return false;
    }

    /**
     * Prints the course.
     *
     * @param student_matric the student matriculation number
     */
    public void printCourse(String student_matric){
        Studentmgr.printCourse(student_matric);
    }


    /**
     * Check vacancy course.
     *
     * @param courseCode the course code
     * @return the vacancy of the course. -1 if unsuccessful.
     */
    public int checkVacancyCourse(String courseCode){
        if(CourseManager.checkCourseExists(courseCode)){
            return CourseManager.getCourseVacancy(courseCode);
        }
        return -1;
    }

    /**
     * Check vacancy course index.
     *
     * @param courseCodeIndex the course code index
     * @return the vacancy of the course index. -1 if unsuccessful.
     */
    public int checkVacancyCourseIndex(String courseCodeIndex){
        String courseCode = courseCodeIndex.substring(0,6);
        System.out.println("COURSE CODE:" + courseCode);
        String courseCodeIndex_last2 = courseCodeIndex.substring(7,9);
        System.out.println("INDEX CODE:" + courseCodeIndex_last2);
        // choice 1 for course vacancy
        // choice 2 for course index vacancy
        if(CourseManager.checkIndexExists(courseCode,courseCodeIndex_last2)){
            return CourseManager.getIndexVacancy(courseCode,courseCodeIndex_last2);
        }
        return -1;
    }

    /**
     * Change index.
     *
     * @param student_matric the student matriculation number
     * @param course_to_drop the course code of the course to drop
     * @param course_to_add the course code of the course to add
     * @return true, if successful
     */
    public boolean changeIndex(String student_matric, String course_to_drop,String course_to_add){
        String courseCode_to_drop = course_to_drop.substring(0,6);
        String courseCode_to_add = course_to_add.substring(0,6);
        String courseCodeIndex_to_drop_last2 = course_to_drop.substring(7,9);
        String courseCodeIndex_to_add_last2 = course_to_add.substring(7,9);
        if(!courseCode_to_drop.equals(courseCode_to_add))
        {
        	System.out.println("Change index only allowed for same course");
        	return false;
        }
        if(course_to_drop.equals(course_to_add)){
            System.out.println("Courses are identical!");
            return false;
        }
        if(!Studentmgr.checkCourse(student_matric,course_to_drop)){
            System.out.println("Student is not taking the course!");
            return false;
        }

        if(CourseManager.checkCourseExists(courseCode_to_drop)){
            // remove student from old course
            CourseManager.removeStudent(courseCode_to_drop,courseCodeIndex_to_drop_last2,student_matric);

            // remove old course timetable from student
            int[][] courseTimetable_toRemove = CourseManager.getTimeSlot(courseCode_to_drop,courseCodeIndex_to_drop_last2);
            Studentmgr.updateTimetable(student_matric,0,courseTimetable_toRemove);

            // if no clash
            if(checkTimetable(student_matric,course_to_add)){
                if(!CourseManager.addStudent(courseCode_to_add,courseCodeIndex_to_add_last2,student_matric)){
                    Studentmgr.updateTimetable(student_matric,1,courseTimetable_toRemove);
                    System.out.println("There is no more vancancies!");
                    return false;
                }

                try{
//                    System.out.println("updated");
                    Studentmgr.updateTimetable(student_matric,1,courseTimetable_toRemove);
                    dropCourse(student_matric,course_to_drop);
                    Studentmgr.updateAU(student_matric, CourseManager.getCourseAU(courseCode_to_add),1);
                }
                catch (Exception e){
                    System.out.println(e+" has occured");
                }
                // add new course timetable from student
                int[][] courseTimetable_toAdd = CourseManager.getTimeSlot(courseCode_to_add,courseCodeIndex_to_add_last2);
                Studentmgr.updateTimetable(student_matric,1,courseTimetable_toAdd);
                // update student course list
                Studentmgr.changeIndex(student_matric,course_to_drop,course_to_add);
                return true;
            }
            //revert changes
            else{

                Studentmgr.updateTimetable(student_matric,1,courseTimetable_toRemove);
                System.out.println("Timetable clashes with new index!");
                return false;
            }
        }
        return false;
    }

    /**
     * Prints the student info.
     *
     * @param student_matric the student matriculation number
     */
    public void printStudentInfo(String student_matric){
        Studentmgr.printStudentInfo(student_matric);
    }

    /**
     * Prints the timetable.
     *
     * @param student_matric the student matriculation number
     * @return true, if successful
     */
    public boolean printTimetable(String student_matric){
        return Studentmgr.printTimetable(student_matric);
    }

    /**
     * Gets the all students.
     */
    public void getAllStudents(){
        Studentmgr.getAllStudents();
    }

    /**
     * Finds the student course index when given a course code
     *
     * @param student_matric the student matriculation number
     * @param courseCode the course code
     * @return the index code corresponding to the course that the student has registered to
     */
    public String findStudentCourseIndex(String student_matric,String courseCode){
        return Studentmgr.findStudentCourseIndex(student_matric,courseCode);
    }

    /**
     * Finds all the courses available in the system
     *
     * @return ArrayList containing all the course code
     */
    public List<String> getAllCourses(){
        List<String> allCourses =  CourseManager.getAllCourses();
        return allCourses;
    }

    /**
     * Finds all the course index available in the course
     * @param courseCode the course code
     * @return ArrayList containing all the course index
     */
    public List<String> getAllIndex(String courseCode){
        return CourseManager.getAllIndex(courseCode);
    }
}
