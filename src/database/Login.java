package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Login { 
	public static List<String> getid(String type){
		List<String> all_ids=new ArrayList<String>();
		Connection connection=null;
		if(type.equalsIgnoreCase("airline")){
			try{
				connection=getConnection();
				PreparedStatement pstmt=
				connection.prepareStatement("select airline_id from airline");
				ResultSet rs= pstmt.executeQuery();
				while (rs.next()){
					all_ids.add(rs.getString(1));
				}
			} catch(SQLException sqle){
				System.out.println("SQL exception when getting all id's from airline");
			} finally{
				closeConnection(connection);
			}
		}
		if(type.equalsIgnoreCase("enterprise")){
			try{
				connection=getConnection();
				PreparedStatement pstmt=
				connection.prepareStatement("select company_id form company");
				ResultSet rs= pstmt.executeQuery();
				while (rs.next()){
					all_ids.add(rs.getString(1));
				}
			} catch(SQLException sqle){
				System.out.println("SQL exception when getting all id's from company");
			} finally{
				closeConnection(connection);
			}
		}
		return all_ids;
	}
	public static boolean admin_access(String username, String password){
		boolean ispresent=false;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select * from employee where employee_id=? and passwd=? and isadmin=t");
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				ispresent=true;
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception when checking for admin");
		} finally{
			closeConnection(connection);
		}
		return ispresent;
	}
	public static boolean employee_access(String username, String password){
		boolean ispresent=false;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select * from employee where employee_id=? and passwd=?");
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				ispresent=true;
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception when checking employee");
		} finally{
			closeConnection(connection);
		}
		return ispresent;
	}
	public static boolean airline_access(String username, String password){
		boolean ispresent=false;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select * from airline where airline_id=? and passwd=?");
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			ResultSet rs= pstmt.executeQuery();			
			while (rs.next()){
				ispresent=true;
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception when checking airline");
		} finally{
			closeConnection(connection);
		}
		return ispresent;
	}
	public static boolean company_access(String username, String password){
		boolean ispresent=false;
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select * from company where company_id=? and passwd=?");
			pstmt.setString(1,username);
			pstmt.setString(2, password);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				ispresent=true;
			}
		} catch(SQLException sqle){
			System.out.println("SQL exception when checking company");
		} finally{
			closeConnection(connection);
		}
		return ispresent;
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
