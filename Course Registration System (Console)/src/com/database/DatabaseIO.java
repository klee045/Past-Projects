package com.database;

import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DatabaseIO {

	/** The Constant SEPARATOR. */
	private static final String SEPARATOR = "|";

//	public List<Student> readStudent(String filename) throws IOException
//	{
//	    List<Student> studentList = new ArrayList<Student>();
//		ArrayList stringArray = (ArrayList) read(filename);
//		for (int i = 0; i < stringArray.size(); i++) {
//			String st = (String) stringArray.get(i);
//			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
//			String matricNo = star.nextToken().trim();
//			String name = star.nextToken().trim();
//			String gender = star.nextToken().trim();
//			String nationality= star.nextToken().trim();
//			Student student = new Student(matricNo,name,gender,nationality);
//			studentList.add(student);
//		}
//		return studentList;
//	}
//
//
//	public boolean saveCourses(String filename, CourseDB courseDB) throws IOException {
//		List<Course> courseList = courseDB.getCourseList();
//		List data = new ArrayList();
//        for (Course c: courseList) {
//				StringBuilder st =  new StringBuilder() ;
//				st.append(c.getCode().trim());
//				st.append(SEPARATOR);
//				for(CourseIndex ci: c.getIndex())
//				{
//					st.append(ci.getCode().trim());
//					st.append(SEPARATOR);
//				}
//				data.add(st.toString()) ;
//			}
//			write(filename,data);
//		return true;
//	}
//
//	public List<Course> readCourse(String filename,StudentDB studentDB) throws IOException
//	{
//	    List<Course> courseList = new ArrayList<Course>();
//		ArrayList stringArray = (ArrayList) read(filename);
//		for (int i = 0; i < stringArray.size(); i++) {
//			String st = (String) stringArray.get(i);
//			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
//			List<CourseIndex> index= new ArrayList<CourseIndex>();
//			String course_code = star.nextToken().trim();
//			String course_name = star.nextToken().trim();
//			while(star.hasMoreTokens())
//			{
//				String index_code = star.nextToken().trim();
//				index.add(new CourseIndex(index_code, totalSlot, timeSlot));
//			}
//			Course course = new Course(course_code, course_name, index, AU);
//			courseList.add(course);
//		}
//		return courseList;
//	}
//
//	public List<Admin> readAdmin(String filename) throws IOException
//	{
//		List<Admin> adminList = new ArrayList<Admin>();
//		ArrayList stringArray = (ArrayList) read(filename);
//		for (int i = 0; i < stringArray.size(); i++) {
//			String st = (String) stringArray.get(i);
//			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
//			String name = star.nextToken().trim();
//			String password = star.nextToken().trim();
////				int  contact = Integer.parseInt(star.nextToken().trim()); // third token
//			Admin admin = new Admin(name,password);
//			adminList.add(admin);
//		}
//
//		return adminList;
//	}
//
//	public boolean saveAdmins(String filename, AdminDB adminDB) throws IOException {
//		List<Admin> adminList = adminDB.getAdminList();
//		List data = new ArrayList();
//        for (Admin a: adminList) {
//				StringBuilder st =  new StringBuilder() ;
//				st.append(a.getName().trim());
//				st.append(SEPARATOR);
//				st.append(a.getPassword().trim());
//				data.add(st.toString()) ;
//			}
//			write(filename,data);
//		return true;
//	}
//
//
//	public void readRecord(String filename,StudentDB studentDB,CourseDB courseDB) throws IOException
//	{
//	    List<Course> courseList = new ArrayList<Course>();
//		ArrayList stringArray = (ArrayList) read(filename);
//		for (int i = 0; i < stringArray.size(); i++) {
//			String st = (String) stringArray.get(i);
//			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
//			List<CourseIndex> index= new ArrayList<CourseIndex>();
//			String course_code = star.nextToken().trim();
//			String index_code = star.nextToken().trim();
//	    	for(Course c: courseDB.getCourseList())
//	    	{
//
//		    	if(course_code.equals(c.getCode()))
//		    	{
//		    		Course course= c;
//		    		for(CourseIndex courseIndex: course.getIndex())
//		    		{
//				    	if(index_code.equals(courseIndex.getCode()))
//				    	{
//				    		CourseIndex index1= courseIndex;
//							while(star.hasMoreTokens())
//							{
//								String matricNo = star.nextToken().trim();
//					    		for(Student student: studentDB.getStudentList())
//					    		{
//					    			if(matricNo.equals(student.getMatricNo()))
//					    			{
//					    				student.register(index1);
//					    			}
//
//					    		}
//							}
//
//				    	}
//		    		}
//		    	}
//	    	}
//		}
//	}
////	public boolean saveRecord(String filename,CourseDB courseDB) throws IOException {
////		List<Course> courseList = courseDB.getCourseList();
////		List data = new ArrayList();
////        for (Course c: courseList) {
////        		for(CourseIndex ci: c.getIndex())
////				{
////	        		StringBuilder st =  new StringBuilder() ;
////					st.append(c.getCode().trim());
////					st.append(SEPARATOR);
////					st.append(ci.getCode().trim());
////					st.append(SEPARATOR);
////					for(Student s:ci.getStudents())
////					{
////						st.append(s.getMatricNo().trim());
////						st.append(SEPARATOR);
////					}
////					data.add(st.toString()) ;
////				}
////			}
////			write(filename,data);
////		return true;
/**
 * Read data.
 *
 * @param filename the input filename
 * @return Object to be downcasted to Database class
 * @throws IOException Signals that an I/O exception has occurred.
 */
////	}
	public  Object readData (String filename) throws IOException
	{
		 Object object;
	      try {
	          FileInputStream fileIn = new FileInputStream(filename);
	          ObjectInputStream in = new ObjectInputStream(fileIn);
	          object =  in.readObject();
	          in.close();
	          fileIn.close();
	       } catch (IOException i) {
	          i.printStackTrace();
	          return null;
	       } catch (ClassNotFoundException c) {
	          c.printStackTrace();
	          return null;
	       }
	      return object;
	}

	/**
	 * Serialize database into ser file.
	 *
	 * @param filename output filename
	 * @param db Database object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeData(String filename, Database db) throws IOException
	{
		  List<Object> objectList = new ArrayList<Object>();
	      try {
				  FileOutputStream fileOut = new FileOutputStream(filename);
				  ObjectOutputStream out = new ObjectOutputStream(fileOut);
				  out.writeObject(db);
				  out.close();
				  fileOut.close();
	            System.out.println("Serialized data is saved in "+ filename);
	         } catch (IOException i) {
	            i.printStackTrace();
	         }
	}

//
//	public void writeAdmin (String filename, AdminDB db) throws IOException
//	{
//		  List<Object> objectList = new ArrayList<Object>();
//	      try {
//				  FileOutputStream fileOut = new FileOutputStream(filename);
//				  ObjectOutputStream out = new ObjectOutputStream(fileOut);
//				  out.writeObject(db.getAdminList());
//				  out.close();
//				  fileOut.close();
//	            System.out.println("Serialized data is saved in "+ filename);
//	         } catch (IOException i) {
//	            i.printStackTrace();
//	         }
//	}
//	public void writeStudents (String filename, StudentDB db) throws IOException
//	{
//		  List<Object> objectList = new ArrayList<Object>();
//	      try {
//				  FileOutputStream fileOut = new FileOutputStream(filename);
//				  ObjectOutputStream out = new ObjectOutputStream(fileOut);
//				  out.writeObject(db.getStudentList());
//				  out.close();
//				  fileOut.close();
//	            System.out.println("Serialized data is saved in "+ filename);
//	         } catch (IOException i) {
//	            i.printStackTrace();
//	         }
//	}
//	public void writeCourse (String filename, CourseDB db) throws IOException
//	{
//		  List<Object> objectList = new ArrayList<Object>();
//	      try {
//				  FileOutputStream fileOut = new FileOutputStream(filename);
//				  ObjectOutputStream out = new ObjectOutputStream(fileOut);
//				  out.writeObject(db.getCourseList());
//				  out.close();
//				  fileOut.close();
//	            System.out.println("Serialized data is saved in "+ filename);
//	         } catch (IOException i) {
//	            i.printStackTrace();
//	         }
//	}
//	  /** Read the contents of the given file. */
//	  public static List read(String fileName) throws IOException {
//		List data = new ArrayList() ;
//	    Scanner scanner = new Scanner(new FileInputStream(fileName));
//	    try {
//	      while (scanner.hasNextLine()){
//	        data.add(scanner.nextLine());
//	      }
//	    }
//	    finally{
//	      scanner.close();
//	    }
//	    return data;
//	  }
//
//	  public static void write(String fileName, List data) throws IOException  {
//		    PrintWriter out = new PrintWriter(new FileWriter(fileName));
//
//		    try {
//				for (int i =0; i < data.size() ; i++) {
//		      		out.println((String)data.get(i));
//				}
//		    }
//		    finally {
//		      out.close();
//		    }
//	}
}
