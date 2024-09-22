package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.Location;

public class LocationCountersRegistersDAO {

	public void createTableLocationCountersRegisters(int idLocation) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS locationcountersregisters_"+idLocation+
				" (id INTEGER not NULL auto_increment primary key,id_location INTEGER not NULL,month VARCHAR(250)  NOT NULL,year VARCHAR(250) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}
	//verify if table Register is Empty
		public int verifyTableLocationCountersRegisters(int idLocation) throws SQLException {
			
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String selectUser = "SELECT * FROM locationcountersregisters_"+idLocation;
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
		
		//verify if table Register is Empty
				public int verifyTableLocationCountersRegistersContainMonthYear(int idLocation,String month,String year) throws SQLException {
					
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String selectUser = "SELECT * FROM locationcountersregisters_"+idLocation;
					int count = 0;	
				    Statement stmt = (PreparedStatement) con.prepareStatement(selectUser);
				    ResultSet rs = stmt.executeQuery(selectUser);
				        try {
				             while(rs.next()){
				            	 int id = rs.getInt("id");
						    	  String monthly = rs.getString("month");
						    	  String yearLocal = rs.getString("year");
						    	  if((month.equalsIgnoreCase(monthly))&&(year.equalsIgnoreCase(yearLocal))) {
				                count++;
						    	  }
				             }
				            
				        } catch (SQLException ex) {
				                
				         }
				  
				     helper.closeConnection(con);
				     return count;
				}
		
		//Insert  into table Register
		public void insertLocationCountersRegisters(int idLocation,String month,String year) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String insertPay = "insert into locationcountersregisters_"+idLocation+ 
					"(id_location,month,year)"
					  +" value(?,?,?)";		
			PreparedStatement ps = con.prepareStatement(insertPay);
			ps.setInt(1, idLocation);
			ps.setString(2, month);
			ps.setString(3, year);
			ps.executeUpdate();
			
			 helper.closeConnection(con);
		      }


		//get last ChasRegister
		public Location getLastLocationCountersRegisters(int idLocation) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			Location loc = new Location();
			
			String strSql = ("SELECT * FROM locationcountersregisters_"+idLocation+ " ORDER BY id DESC LIMIT 1");
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
			public Location getTwoLocationCountersRegisters(int idLocation) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location loc = new Location();
				
				String strSql = ("SELECT * FROM locationcountersregisters_"+idLocation+ " ORDER BY id DESC LIMIT 2");
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
			public Location getByIdLocationCountersRegisters(int id,int idLocation) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location loc = new Location();
				
				String strSql = ("SELECT * FROM locationcountersregisters_"+idLocation+ " WHERE id="+id);
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
			public ArrayList<Location> getAllLocationListRegisters(int idLocation) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location loc = new Location();
				ArrayList<Location> locList = new ArrayList<Location>();
				String strSql = ("SELECT * FROM locationcountersregisters_"+idLocation);
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String monthly = rs.getString("month");
			    	  String year = rs.getString("year");
			    	  loc = new Location(id,idLocation,monthly,year);
			    	  locList.add(loc);
			      }
				return locList;
			}
			
			//Delete LineChasRegister
			public void deleteLineLocationListRegisters(int id,int idLocation) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DELETE FROM locationcountersregisters_"+idLocation+" WHERE id='"+id+"'";
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
          }	
			
			//DROP TableChasRegister
			public void dropLocationListRegisters(int idLocation) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DROP TABLE IF EXISTS locationcountersregisters_"+idLocation;
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
          }
		
}
