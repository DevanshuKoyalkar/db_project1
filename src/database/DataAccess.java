package database;

import java.sql.*;
import java.util.*;


public class DataAccess {
	public static List<String> getCourseList(String id) {
		List<String> courseList=new ArrayList<String>();
		Connection connection=null;
		
		try{
			connection=getConnection();
			PreparedStatement pstmt= connection.prepareStatement("select distinct course_id from takes where id=?");
			pstmt.setString(1, id);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				courseList.add(rs.getString(1));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting course list");
		} finally{
			closeConnection(connection);
		}
		
		return courseList;
	}
	
	static Map<String,Integer> getYearCredit(String id) {
		Map<String,Integer> yearwiseList=new HashMap<String,Integer>();
		return yearwiseList;
	}
	
	
	static List<String> getInstructors(){
		List<String> courseList=new ArrayList<String>();
		return courseList;
	}
	
	static Connection getConnection() {
		String dbURL = "jdbc:postgresql://10.105.1.12/cs387";
        String dbUser = "db130050080";
        String dbPass = "rvd/rkomvp";
        Connection connection=null;
        try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
        } catch(ClassNotFoundException cnfe){
        	System.out.println("JDBC Driver not found");
        } catch(SQLException sqle){
        	System.out.println("Error in getting connetcion from the database");
        }
        
        return connection;
	}
	
	static void closeConnection(Connection connection) {
		try{
			connection.close();
		} catch(SQLException sqle) {
			System.out.println("Error in close database connetcion");
		}
	}
}
