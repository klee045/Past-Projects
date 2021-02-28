package com.users;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class User implements java.io.Serializable{

    /** String username. */
    private String username;

    /** String password. Must be stored as a hashed string. */
    private String password;

    /** LocalDate accPerStart
     * Stored for comparing whether user is able to access STARS
     * Access Period Start. */
    private LocalDate accPerStart;

    /**	LocalDate accPerEnd
     * Stored for comparing whether user is able to access STARS
     * Access Period End. */
    private LocalDate accPerEnd;
    //accessPeriodStart string and accessPeriodEnd string must be in YYYYMMDD format for parsing into LocalDate
    //Java serialization causes formatter to become null, DateTimeFormatter data type to be instantiated within the constructor itself
    //private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    //Store the way date is to be stored instead.

    /**  String format  Format for all LocalDates to be parsed. */
    //LocalDate to be parsed in yyyyMMdd format: (e.g 20201117)
    private final static String format = "yyyyMMdd";

    /** The Constant stringformat. */
    private final static String stringformat = "dd LLLL yyyy";

    /**
     * Instantiates a new user.
     */
    public User() {
    }

    /**
     * Instantiates a new user.
     * @param username username
     * @param password password
     * @param accessPeriodStart the access period start date of user (format: YYYYMMDD)
     * @param accessPeriodEnd the access period end date of user (format: YYYYMMDD)
     */
    public User(String username, String password, String accessPeriodStart, String accessPeriodEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    	this.username = username;
        this.password = password;
        this.accPerStart = LocalDate.parse(accessPeriodStart,formatter);
        this.accPerEnd = LocalDate.parse(accessPeriodEnd, formatter);
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
    	return username;
    }

    /**
     * Gets Access Period Start.
     * @return accPerStart
     */
    public LocalDate getAccPerStart() {
    	return accPerStart;
    }

    /**
     * Gets Access Period End.
     *
     * @return accPerEnd
     */
    public LocalDate getAccPerEnd() {
    	return accPerEnd;
    }

    /**
     * Sets access period start date
     * @param accPerStart access period start date (format: YYYYMMDD)
     */
    public void setAccPerStart(String accPerStart) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    	this.accPerStart = LocalDate.parse(accPerStart,formatter);
	}

    /**
     * Sets access period end date
     * @param accPerEnd access period end date (format: YYYYMMDD)
     */
    public void setAccPerEnd(String accPerEnd) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    	this.accPerEnd = LocalDate.parse(accPerEnd,formatter);
	}

    /**
     * Converts the date from String to DateTime
     * @param accPer the access period string
     * @return true if successful
     * @throws DateTimeException if access period string given is an invalid date
     */
    public static boolean parseDate(String accPer) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    	try {
    		LocalDate.parse(accPer,formatter);
    		return true;
    	}
    	catch (DateTimeException e) {
    		return false;
    	}
    }

    /**
     * Convert date from LocalDate object to String
     *
     * @param accPer the LocalDate object
     * @return the date is string format
     */
    public static String convertDate(LocalDate accPer) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(stringformat);
    	try {
    		String formattedString = accPer.format(formatter);
    		return formattedString;
    	}
    	catch (Exception e) {
    		return null;
    	}
    }
}
