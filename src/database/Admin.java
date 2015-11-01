package database;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

public class Admin {
	public static void updatePending(String request_id, String option) throws ParseException{
		Connection connection=null;
		try{
			connection=getConnection();
			if(option=="reject"){
				//update the row
				//update pending set status='rejected' where request_id=request_id - completed
				PreparedStatement pstmt=
				connection.prepareStatement("update pending set status='rejected' where request_id=?");
				pstmt.setString(1, request_id);
				ResultSet rs= pstmt.executeQuery();
			}
			else{
			
				//update the row and remove rows which are interfering
				//update pending set status='accepted' where request_id=request_id
				PreparedStatement pstmt1=
				connection.prepareStatement("update pending set status='accepted' where request_id=?");
				pstmt1.setString(1, request_id);
				
				
				pstmt1.executeUpdate();
				PreparedStatement pstmt2=
				connection.prepareStatement("select request_type, company_id, object_id, book_start,book_end from pending where request_id=?");
				pstmt2.setString(1, request_id);
				
				
				ResultSet rs= pstmt2.executeQuery();
				
				System.out.println("Came This  check point");
				
				while(rs.next()){
					
					String request_type = rs.getString(1);
					String company_id = rs.getString(2);
					String object_id = rs.getString(3);
					
					System.out.println(company_id+"   "+object_id);
				
					
					
					if( request_type.equalsIgnoreCase("shop")){
						PreparedStatement pstmt3 = 
								connection.prepareStatement("insert into shop_booking values(?,?,?,?)");
						pstmt3.setString(1, object_id);
						pstmt3.setString(2, company_id);
						pstmt3.setDate(3,rs.getDate(4));
						pstmt3.setDate(4, rs.getDate(5));
						System.out.println(pstmt3);
						pstmt3.executeUpdate();
						
					}
					
					if( request_type.equalsIgnoreCase("gate")){
						PreparedStatement pstmt3 = 
								connection.prepareStatement("insert into gate_booking values(?,?,?,?)");
						pstmt3.setString(1, object_id);
						pstmt3.setString(2, company_id);
						pstmt3.setDate(3,rs.getDate(4));
						pstmt3.setDate(4, rs.getDate(5));
						System.out.println(pstmt3);
						pstmt3.executeUpdate();
						
					}
					
					if( request_type.equalsIgnoreCase("warehouse")){
						PreparedStatement pstmt3 = 
								connection.prepareStatement("insert into warehouse_booking values(?,?,?,?)");
						pstmt3.setString(1, object_id);
						pstmt3.setString(2, company_id);
						pstmt3.setDate(3,rs.getDate(4));
						pstmt3.setDate(4, rs.getDate(5));
						System.out.println(pstmt3);
						pstmt3.executeUpdate();
						
					}
					
					
					
				}
						
						
			}
			
			
			System.out.println("Came till date clash");
			PreparedStatement pstmt4 = connection.prepareStatement("select * from pending where status = 'pending'");
			ResultSet rs = pstmt4.executeQuery();
			System.out.println("After the first query execution");
			
			while(rs.next()){
				boolean dateCheck = checkDateAtAdmin(rs,connection);
				if(dateCheck == false){
					System.out.println("DateClash at Admin accepting the request");
					String requestID = rs.getString(1);
					PreparedStatement pstmt=
							connection.prepareStatement("update pending set status='rejected' where request_id=?");
							pstmt.setString(1, requestID);
							pstmt.executeUpdate();
					
					
				}
			}
			
			
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when accepting ");
		} finally{
			closeConnection(connection);
		}
		return;
	}
	
	
	
	public static boolean checkDateAtAdmin(ResultSet rs, Connection connection) throws ParseException, SQLException{
		boolean ret  = true;
		
		String option = rs.getString(2);
		String obj_id = rs.getString(6);
		java.sql.Date  startDate = rs.getDate(7);
		java.sql.Date  endDate = rs.getDate(8);
		
		
		
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
			ResultSet rs2 = pstmt.executeQuery();
			System.out.println(pstmt);
			while(rs2.next()){
				ret = false;
			}
			System.out.println(ret);
		
			}
		
		else if(option.equalsIgnoreCase("warehouse")){
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
			ResultSet rs2 = pstmt.executeQuery();
			
			while(rs2.next()){
				ret = false;
			}
		
			}
		
		else if(option.equalsIgnoreCase("shop")){
			PreparedStatement pstmt = connection.prepareStatement("select book_start, book_end from shop_booking "
					+ " where shop_id = ?  and (( book_start <= ? and book_end >= ?) or "
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
			ResultSet rs2 = pstmt.executeQuery();
			System.out.println("After query");
			while(rs2.next()){
				ret = false;
			}
			System.out.println(ret);
		}
		
		
		
		return ret;
	}
	
	
	
	
	
	
	
	public static List<String> getShops(){
		List<String> warehouses=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select request_id,object_id,company_id,book_start,book_end from pending where request_type='shop' and status='pending'");
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				warehouses.add(rs.getString(1));
				warehouses.add(rs.getString(2));
				warehouses.add(rs.getString(3));
				warehouses.add(rs.getString(4));
				warehouses.add(rs.getString(5));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting pending shops");
		} finally{
			closeConnection(connection);
		}
		return warehouses;
	}
	public static List<String> getWarehouses(){
		List<String> warehouses=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select request_id,object_id,company_id,book_start,book_end from pending where request_type='warehouse' and status='pending'");
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				warehouses.add(rs.getString(1));
				warehouses.add(rs.getString(2));
				warehouses.add(rs.getString(3));
				warehouses.add(rs.getString(4));
				warehouses.add(rs.getString(5));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting pending warehouses");
		} finally{
			closeConnection(connection);
		}
		return warehouses;
	}
	public static List<String> getGates(){
		List<String> warehouses=new ArrayList<String>();
		Connection connection=null;
		try{
			connection=getConnection();
			PreparedStatement pstmt=
			connection.prepareStatement("select request_id,object_id,company_id,book_start,book_end from pending where request_type='gate' and status='pending'");
			ResultSet rs= pstmt.executeQuery();
			while (rs.next()){
				warehouses.add(rs.getString(1));
				warehouses.add(rs.getString(2));
				warehouses.add(rs.getString(3));
				warehouses.add(rs.getString(4));
				warehouses.add(rs.getString(5));
			}
			
		} catch(SQLException sqle){
			System.out.println("SQL exception when getting pending gates");
		} finally{
			closeConnection(connection);
		}
		return warehouses;
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
