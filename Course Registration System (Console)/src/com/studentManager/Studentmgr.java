package com.studentManager;

import com.users.Student;
import com.database.StudentDB;

import java.util.*;

public class Studentmgr {

    /** The AU limit. */
    private static int AUlimit = 18;

    /** The student DB. */
    private StudentDB studentDB;

    /**
     * Constructor for the class
     * Instantiates a new studentmgr.
     *
     * @param studentDB the student DB
     */
    public Studentmgr(StudentDB studentDB){
        this.studentDB = studentDB;
    }


    /**
     * Gets the total AU of the student
     *
     * @param student_matric number
     * @return the AU
     */
    public int getAU(String student_matric){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            return student_object.getTotalAU();
        }
        return -1;
    }

    /**
     * Check if new AU exceeded
     *
     * @param student_matric the student matriculation number
     * @param AU_to_change AU of new course to change
     * @return true if new AU under limit
     */
    public  boolean checkAU(String student_matric, int AU_to_change){
        int total_AU;
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            total_AU = getAU(student_matric);
            if ((AUlimit >= (total_AU + AU_to_change))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update AU.
     *
     * @param student_matric the student matriculation number
     * @param AU_to_change the number of AU to add or substract
     * @param choice the choice. 1 represents add. 0 represents minus
     * @return true, if successful
     */
    public boolean updateAU(String student_matric, int AU_to_change,int choice){
        // choice = 1 represents add
        // choice = 0 represents minus
        int total_AU;
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            total_AU = getAU(student_matric);
            if(choice == 1 && checkAU(student_matric,AU_to_change)){
                student_object.changeAU(AU_to_change,choice);
                return true;
            }
            else if(choice == 0){
                student_object.changeAU(AU_to_change,choice);
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Gets the Email from the student
     *
     * @param student_matric the student matriculation number
     * @return the email of the student
     */
    public String getEmail(String student_matric){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            return student_object.getEmail();
        }
        return null;
    }

    /**
     * Gets the message type from the student
     *
     * @param student_matric the student matriculation number
     * @return the student's preferred notification type
     */
    public String getMessageType(String student_matric){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            return student_object.getNotifPreference();
        }
        return null;
    }


    /**
     * Update timetable.
     *
     * @param student_matric the student matriculation number
     * @param choice the choice. 1 - add course, 0 - drop course
     * @param courseTimetable the course timetable
     * @return true, if successful
     */
    public boolean updateTimetable(String student_matric,int choice,int[][]  courseTimetable){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            student_object.updateTimetable(choice,courseTimetable);
            return true;
        }
        return false;
    }


    /**
     * Gets the student timetable.
     *
     * @param student_matric the student matriculation number
     * @return the student timetable
     */
    public int[][] getStudentTimetable(String student_matric){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            return student_object.getTimetable();
        }
        return null;
    }

    /**
     * Adds the course to the student's registered courses
     *
     * @param student_matric the student matriculation number
     * @param courseCodeIndex the course code index
     * @return true, if successful.
     * false, if matric number if invalid or course already registered
     */
    public  boolean addCourse(String student_matric,String courseCodeIndex){
//        Check if student exist in our DB
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);
//            List<String> courseEnrolled = student_object.getCourseEnrolled();
//            Check if student is already enrolled in the course
            if (checkCourse(student_matric,courseCodeIndex)==false) {
                student_object.register(courseCodeIndex);
                return true;
            }
        }
        return false;
    }

    /**
     * drop the course for the student
     *
     * @param student_matric the student matriculation number
     * @param courseCodeIndex the course code index
     * @return true, if successful
     * false, if matric number if invalid or course not registered
     */
    public  boolean dropCourse(String student_matric,String courseCodeIndex){
        // need to add check if the course index is valid
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);
            List<String> courseEnrolled = student_object.getCourseEnrolled();
            if (checkCourse(student_matric,courseCodeIndex)==false) {
                return false;
            }
            else{
                for (String ci : courseEnrolled) {
                    if(ci.equals(courseCodeIndex)){
                        courseEnrolled.remove(ci);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if student is registered for the course
     *
     * @param student_matric the student matric
     * @param courseCodeIndex the course code index
     * @return true, if successful
     * false, if matric number if invalid or course not registered
     */
    public boolean checkCourse(String student_matric,String courseCodeIndex){
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);

            for (String ci : student_object.getCourseEnrolled()) {
                if(ci.equals(courseCodeIndex)){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    /**
     * Check if the course is in the student's waiting list
     *
     * @param student_matric the student matric number
     * @param courseCodeIndex course code index to check
     * @return true if is waiting list
     * false, if matric number if invalid or course not in waiting list
     */
    public boolean checkWaitlist(String student_matric,String courseCodeIndex){
        String courseCode = courseCodeIndex.substring(0,6);
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);

            for (String ci : student_object.getCourseWaitlist()) {
                String courseCode_to_test = ci.substring(0,6);

                if(courseCode_to_test.equals(courseCode)){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    /**
     * Prints the course that the student is registered in
     *
     * @param student_matric the student matriculation number
     */
    public void printCourse(String student_matric){
        int count = 1;
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);
            System.out.printf("Courses registered for %s:%n",student_matric);
            for (String ci : student_object.getCourseEnrolled()) {
                System.out.printf("%d. %s %n",count,ci);
                count++;
            }
            return;
        }
        else{
            return;
        }
    }

    /**
     * Swop index with another student that has the same course but different index
     *
     * @param student1 the student 1 matriculation number
     * @param student2 the student 2 matriculation number
     * @param student1_course the student 1 index code
     * @param student2_course the student 2 index code
     * @return true, if successful
     * false, if student matric is invalid
     */
    public boolean swopIndex(String student1,String student2, String student1_course,String student2_course){
        if (checkCourse(student1,student1_course)&&checkCourse(student2,student2_course)){
            dropCourse(student1,student1_course);
            addCourse(student1,student2_course);
            dropCourse(student2,student2_course);
            addCourse(student2,student1_course);
            return true;
        }
       return false;
    }

    /**
     * Change index from one course index to another within the same course
     *
     * @param student the student matriculation number
     * @param course_to_drop the index code to drop
     * @param course_to_add the index code to add
     */
    public void changeIndex(String student, String course_to_drop,String course_to_add){
        dropCourse(student,course_to_drop);
        addCourse(student,course_to_add);
    }

    /**
     * Adds the student to database
     *
     * @param username the username
     * @param password the password
     * @param accessPeriodStart the access period start
     * @param accessPeriodEnd the access period end
     * @param name the name
     * @param gender the gender
     * @param nationality the nationality
     * @param school the school
     * @param email the email address
     */
    // function to pass in string student_matric to add student to database
    public void addStudent(String username, String password, String accessPeriodStart, String accessPeriodEnd,String name,String gender,String nationality,String school,String email){
        Student new_student = new Student(username,password,accessPeriodStart,accessPeriodEnd,name,gender,nationality,school,email);
        studentDB.addStudent(new_student);
    }

    /**
     * Removes the student from the database
     *
     * @param student_matric the student matriculation number
     * @return true, if successful
     * false, if the student cannot be found in the database
     */
    // function to pass in string student_matric to remove student from database and course
    public boolean removeStudent(String student_matric){
        if (studentDB.findStudent(student_matric) != null){
            Student student_object = studentDB.findStudent(student_matric);
            for (String ci : student_object.getCourseEnrolled()) {
                dropCourse(student_matric, ci);
            }
            studentDB.deleteStudent(student_matric);
            return true;
        }
        return false;
    }


    /**
     * Update account period start.
     *
     * @param student_matric the student matriculation number
     * @param accPerStart the account period start
     * @return true, if successful
     * false, if student matric is invalid
     */
    public boolean updateAccPerStart(String student_matric, String accPerStart)
    {
        if (studentDB.findStudent(student_matric) != null){
            Student student = studentDB.findStudent(student_matric);
            student.setAccPerStart(accPerStart);
            return true;
        }
        return false;
    }

    /**
     * Update account period end.
     *
     * @param student_matric the student matriculation number
     * @param accPerEnd the account period end.
     * @return true, if successful
     * false, if student matric is invalid
     */
    public boolean updateAccPerEnd(String student_matric, String accPerEnd)
    {
        if (studentDB.findStudent(student_matric) != null){
            Student student = studentDB.findStudent(student_matric);
            student.setAccPerEnd(accPerEnd);
            return true;
        }
        return false;
    }

    /**
     * Prints the student info (Matric no/ Name/ Gender/ Nationality)
     *
     * @param student_matric the student matriculation number
     */
    public void printStudentInfo(String student_matric){
        if (studentDB.findStudent(student_matric) != null){
            Student student = studentDB.findStudent(student_matric);
            System.out.printf("MatricNo:%s|Student Name:%s| Gender:%s | Nationality:%s\n",student.getMatricNo(),student.getName(),student.getGender(),student.getNationality());
            return;
        }
        System.out.print("Student not Found");
        return;
    }

    /**
     * Prints all the students in the database
     *
     */
    public void getAllStudents(){
        List<Student> studentlist = studentDB.getStudentList();
        int count =1;
        for(Student s: studentlist){
            System.out.printf("%n%d)",count);
            printStudentInfo(s.getMatricNo());
            count++;
        }
    }

    /**
     * printTimetable
     * Prints the Students timetable.
     *
     * @param student_matric the student matriculation number
     * @return true, if successful
     * false, if student matric is invalid
     */
    public boolean printTimetable(String student_matric){

        int hours,minutes,count;
        count = 1;
        String hours_s,minutes_s;
        int[][] sampletimetable;
        if (studentDB.findStudent(student_matric) != null){
            Student student = studentDB.findStudent(student_matric);
            List<String> waitlist = student.getCourseWaitlist();
            System.out.println("Courses in Waitlist:");
            for(String s:waitlist){
                System.out.printf("%d) %s%n",count,s);
                count++;
            }
            sampletimetable = student.getTimetable();
            for (int session = 0; session < 48; session++) {
                if(session==0){
                    System.out.print("Timing"+"\t");
                    System.out.print("Mon"+"\t");
                    System.out.print("Tue"+"\t");
                    System.out.print("Wed"+"\t");
                    System.out.print("Thu"+"\t");
                    System.out.print("Fri"+"\t");
                    System.out.print("Sat"+"\t");
                    System.out.print("Sun"+"\t");
                    System.out.println();
                }
                for (int day = 0; day < 7; day++) {
                    if(session>=14){
                        if(day==0){
                            hours =(session*30)/60;
                            minutes = (session*30)%60;
                            hours_s = String.format("%02d", hours);
                            minutes_s = String.format("%02d", minutes);
                            System.out.print(hours_s+":"+minutes_s+"\t");
                        }
                        System.out.print(sampletimetable[session][day]+"\t");
                        if(day==6){
                            System.out.println();
                        }
                    }

                }

            }
            return true;
        }

        return false;
    }

    /**
     * updateWaitList
     * Updates the waiting list by adding/dropping the course
     *
     * @param student_matric the student matriculation number
     * @param courseCodeIndex the course to be added/dropped
     * @param choice choice to add/drop. 1 - add, 0 - drop
     * @return true if updated successfully,
     * false if student matric is invalid
     */
    public boolean updateWaitlist(String student_matric,String courseCodeIndex,int choice){
        if (studentDB.findStudent(student_matric) != null) {
            Student student = studentDB.findStudent(student_matric);
            student.updateWaitlist(courseCodeIndex,choice);
            return true;
        }
        return false;
    }

    /**
     * returns the courses in waiting list
     *
     * @param student_matric the student matriculation number
     * @return the list of courses that are on waiting list,
     * null - if student matriculation number is invalid
     */
    public List<String> getWaitlist(String student_matric){
        if (studentDB.findStudent(student_matric) != null) {
            Student student = studentDB.findStudent(student_matric);
            return student.getCourseWaitlist();
        }
        return null;
    }

    /**
     * findStudentCourseIndex
     * returns the index for the selected course that the student is taking
     *
     * @param student_matric the student matriculation number
     * @param courseCode the course to the student is taking
     * @return the course index that the student is taking for the input course.
     * false, if student matric is invalid or student is not taking the course
     */
    public String findStudentCourseIndex(String student_matric,String courseCode){
        if (studentDB.findStudent(student_matric) != null) {
            Student student_object = studentDB.findStudent(student_matric);
            for (String ci : student_object.getCourseEnrolled()) {
                if(courseCode.equals(ci.substring(0,6))){
                    return ci.substring(7,9);
                }
            }
        }
        return null;
    }
}


