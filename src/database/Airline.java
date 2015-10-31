package database;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

public class Airline {
	public static void book(String option,String aid,String id, String start,String end) throws ParseException{
		Connection connection = null;
		
		try{
		
			connection=getConnection();
			boolean canInsert = checkDate(id,option,start, end, connection);
			
			if(canInsert == false){
				System.out.println("DateClash, Not possible\n");
				return;
			}
			PreparedStatement pstmt1=connection.prepareStatement("select count(*) from pending");
			ResultSet rs= pstmt1.executeQuery();
			System.out.println("devi put it here");
			int foo=0;
			while(rs.next()){
				foo=rs.getInt(1);
			}
			
			System.out.println(foo);
			System.out.println("Came till here "+" "+aid+" "+id);
			
			String request_id="8"+String.valueOf(foo+2);
//			String request_id = "90";
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date sDate =  df.parse(start);
			java.sql.Date startDate = new java.sql.Date(sDate.getTime());
			
			java.util.Date eDate = df.parse(end);
			java.sql.Date endDate = new java.sql.Date(eDate.getTime());
			
			PreparedStatement pstmt2=
			connection.prepareStatement("insert into pending values(?,?,'pending',now(),?,?,?,?)");
			pstmt2.setString(1,request_id);
			pstmt2.setString(2,option);
			pstmt2.setString(3,aid);
			pstmt2.setString(4,id);
			pstmt2.setDate(5,startDate);
			pstmt2.setDate(6,endDate);
			System.out.println("Came till here done with setting");
			pstmt2.executeUpdate(); 
		} catch(SQLException sqle){
			System.out.println("SQL exception when inserting into pending table");
		} finally{
			closeConnection(connection);
		}
		
		
	}
	
	public static boolean checkDate(String obj_id, String option, String start, String end, Connection connection) throws ParseException, SQLException{
		boolean ret  = true;
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date sDate =  df.parse(start);
		java.sql.Date startDate = new java.sql.Date(sDate.getTime());
		
		java.util.Date eDate = df.parse(end); 
		java.sql.Date endDate = new java.sql.Date(eDate.getTime());
		
		if(option.equalsIgnoreCase("gate")){
			System.out.println("entered the loop");
			PreparedStatement pstmt = connection.prepareStatement("select book_start, book_end from gate_booking "
					+ " where gate_id = ?  and (( book_start <= ? and book_end >= ?) or "
					+ " (book_start <= ? and book_end >= ?) or "
					+ " (book_start >= ? and book_end <= ?)) ");
			pstmt.setString(1, obj_id);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, startDate);
			pstmt.setDate(4, endDate);
			pstmt.setDate(5, endDate);
			pstmt.setDate(6, startDate);
			pstmt.setDate(7, endDate);
			System.out.println(pstmt);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("After query");
			while(rs.next()){
				ret = false;
			}
			System.out.println(ret);
		
			}
		
		if(option.equalsIgnoreCase("warehouse")){
			PreparedStatement pstmt = connection.prepareStatement("select book_start, book_end from warehouse_booking "
					+ " where warehouse_id =? and (( book_start <= ? and book_end >= ?) or "
					+ " (book_start <= ? and book_end >= ?) or "
					+ " (book_start >= ? and book_end <= ?))");
			pstmt.setString(1, obj_id);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, startDate);
			pstmt.setDate(4, endDate);
			pstmt.setDate(5, endDate);
			pstmt.setDate(6, startDate);
			pstmt.setDate(7, endDate);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				ret = false;
			}
		
			}
		
		return ret;
	}
	
	public static List<String> getWarehouse(String airline_id){
		List<String> warehouses=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select warehouse_id,book_start,book_end from warehouse_booking where airline_id=?");
			pstmt.setString(1, airline_id);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				warehouses.add(rs.getString(1));
				warehouses.add(rs.getString(2));
				warehouses.add(rs.getString(3));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting warehouses booked");
		} finally{
			closeConnection(connection);
		}
		return warehouses;
	}
	public static List<String> getGate(String airline_id){
		List<String> gates=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select gate_id,book_start,book_end from gate_booking where airline_id=?");
			pstmt.setString(1, airline_id);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				gates.add(rs.getString(1));
				gates.add(rs.getString(2));
				gates.add(rs.getString(3));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting gates booked");
		} finally{
			closeConnection(connection);
		}
		return gates;
	}
	public static List<String> getPending(String airline_id){
		List<String> pending=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select object_id,book_start,book_end from pending where company_id=?");
			pstmt.setString(1, airline_id);
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				pending.add(rs.getString(1));
				pending.add(rs.getString(2));
				pending.add(rs.getString(3));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting pending requests");
		} finally{
			closeConnection(connection);
		}
		return pending;
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
