package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.Location;

public class LocationListRegisterDAO {

	public void createTableLocationListRegisters() throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS locationregisters"+
				" (id INTEGER not NULL auto_increment primary key,month VARCHAR(250)  NOT NULL,year VARCHAR(250) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}
	

	//verify if table Register is Empty
	public int verifyTableLocationListRegisters() throws SQLException {
		
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String selectUser = "SELECT * FROM locationregisters";
		int count = 0;	
	    Statement stmt = (PreparedStatement) con.prepareStatement(selectUser);
	    ResultSet rst = stmt.executeQuery(selectUser);
	        try {
	             while(rst.next()){
	                count++;
	             }
	            
	        } catch (SQLException ex) {
	                
	         }
	  
	     helper.closeConnection(con);
	     return count;
	}
	
	//Insert  into table Register
	public void insertLocationListRegisters(String month,String year) throws SQLException {
		
		java.util.Date date=new java.util.Date();
		java.sql.Timestamp sqlDate=new java.sql.Timestamp(new java.util.Date().getTime());
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertPay = "insert into locationregisters"+ 
				"( month,year)"
				  +" value(?,?)";		
		PreparedStatement ps = con.prepareStatement(insertPay);
		ps.setString(1, month);
		ps.setString(2, year);
		ps.executeUpdate();
		
		 helper.closeConnection(con);
	      }

	//get last ChasRegister
	public Location getLastLocationListRegisters() throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		Location loc = new Location();
		
		String strSql = ("SELECT * FROM locationregisters ORDER BY id DESC LIMIT 1");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	  String monthly = rs.getString("month");
	    	  String year = rs.getString("year");
	    	  loc = new Location(id,monthly,year);
	      }
		return loc;
	}
	
	//get Two last ChasRegister
		public Location getTwoLocationListRegisters() throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			Location loc = new Location();
			
			String strSql = ("SELECT * FROM locationregisters ORDER BY id DESC LIMIT 2");
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(strSql);
		      while(rs.next()) {
		    	  int id = rs.getInt("id");
		    	  String monthly = rs.getString("month");
		    	  String year = rs.getString("year");
		    	  loc = new Location(id,monthly,year);
		      }
			return loc;
		}
		
		//get ChasRegister
		public Location getLocationListRegisters(int id) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			Location loc = new Location();
			
			String strSql = ("SELECT * FROM locationregisters WHERE id='"+id+"'");
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(strSql);
		      while(rs.next()) {
		    	  //int id = rs.getInt("id");
		    	  String monthly = rs.getString("month");
		    	  String year = rs.getString("year");
		    	  loc = new Location(id,monthly,year);
		      }
			return loc;
		}

		
		//get All ChasRegister
				public ArrayList<Location> getAllLocationListRegisters() throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location loc = new Location();
					ArrayList<Location> locList = new ArrayList<Location>();
					String strSql = ("SELECT * FROM locationregisters ");
					Statement stmt = con.createStatement();
				      ResultSet rs = stmt.executeQuery(strSql);
				      while(rs.next()) {
				    	  int id = rs.getInt("id");
				    	  String monthly = rs.getString("month");
				    	  String year = rs.getString("year");
				    	  loc = new Location(id,monthly,year);
				    	  locList.add(loc);
				      }
					return locList;
				}
				
				//Delete LineChasRegister
				public void deleteLineLocationListRegisters(int id) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DELETE FROM locationregisters WHERE id='"+id+"'";
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					helper.closeConnection(con);
					
	          }	
				
				//DROP TableChasRegister
				public void dropLocationListRegisters() throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DROP TABLE IF EXISTS locationregisters";
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					helper.closeConnection(con);
					
	          }
}
