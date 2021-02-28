package com.database;

public class CourseCodeExistsException extends Exception{
    /**
     * Instantiates a new course code exits exception.
     */
    public CourseCodeExistsException(){
        super("Invalid Code!");
    }
    /**
     * Instantiates a new course code exits exception.
     *
     * @param msg the exception message
     */
    public CourseCodeExistsException(String msg){
        super(msg);
    }
}
