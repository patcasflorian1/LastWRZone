package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.Location;

public class LocationCountersListDAO {

	public void createTableLocationCountersList(int idLocation,String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year
				+" (id INTEGER not NULL auto_increment primary key,mac_location VARCHAR(50) NOT NULL,permanent_contor INTEGER NULL,month_contor DECIMAL(10,2) NULL,day_contor DECIMAL(10,2) NULL,pas_contor DECIMAL(10,2) NULL,number_of_machine INTEGER NULL,signal_level INTEGER NULL,data_entry VARCHAR(50) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}
	
	//verify if table Register is Empty
			public int verifyTableLocationCountersList(int idLocation,String monthly,String year) throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String selectUser = "SELECT * FROM locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year;
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
			//Insert  into table LocationList
			public void insertLocationCountersList(Location location,String monthly,String year) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				String insertLocation = "insert into locationcounterslist_idLocation_"+location.getId()+"__month_"+monthly+"_year_"+year
						+"( mac_location,permanent_contor,month_contor,day_contor,pas_contor,number_of_machine,signal_level,data_entry)"
						  +" value(?,?,?,?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertLocation);
				//ps.setInt(1, location.getId());
				
				ps.setString(1,location.getLocationMacAddress());
				ps.setDouble(2, location.getPermanentContor());
				ps.setDouble(3, location.getMonthContor());
				ps.setDouble(4, location.getDayContor());
				ps.setDouble(5, location.getPasContor());
				ps.setInt(6, location.getNumberOfMachine());
				ps.setInt(7, location.getSignalLevel());
				ps.setString(8, paymentDate);
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }
			
			//get allLocationList
			public ArrayList<Location> getAllLocationCountersList(int idLocation,String monthly,String year) throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				 ArrayList<Location> chRegList = new ArrayList<Location>();
				String strSql = ("SELECT * FROM locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year);
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    
			    	  String macAddress = rs.getString("mac_location");
			    	  double permanentContor = rs.getDouble("permanent_contor");
			    	 double monthContor = rs.getDouble("month_contor");
			    	  double dayContor =rs.getDouble("day_contor");
			    	  double pasContor =rs.getDouble("pas_contor");
			    	  int numberOfMachine = rs.getInt("number_of_machine");
			    	  int signalLevel = rs.getInt("signal_level");
			    	  String date = rs.getString("data_entry");
			    	    
			    	    // SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
			    	     //String dataEntry =  formatter.format(date);
			    	  chReg = new Location(id,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
			    
			    	  chRegList.add(chReg);
			      }
				return chRegList;
			}
			
			//Update table LocationContor by MAC ADDRESS
			public void updateLocationCountersList(Location location,int idLocation,String monthly,String year) throws SQLException {
				
				LocalDateTime now = LocalDateTime.now();
				ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
				String paymentDate =zonedDateTime.toString();
				 String regex = "[T.]";
				String myArray[] = paymentDate.split(regex); 
				 paymentDate = myArray[0]+" "+myArray[1];
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String updateLocationList = "UPDATE locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year
				+" SET month_contor = '"+location.getMonthContor()+"'"
				+ ",day_contor = '"+location.getDayContor()+"'"
				+ " where id = '"+location.getId()+"'";
						 
				PreparedStatement ps = con.prepareStatement(updateLocationList);
				ps.executeUpdate();		
				 helper.closeConnection(con);
			      }
			//get Location by id
			public Location getLocation(int id,int idLocation,String monthly,String year) throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				Location chReg = new Location();
				String strSql = ("SELECT * FROM locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year+" where id = "+id);
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  
			    	  String macAddress = rs.getString("mac_location");
			    	  double permanentContor = rs.getDouble("permanent_contor");
			    	  double monthContor = rs.getDouble("month_contor");
			    	  double dayContor =rs.getDouble("day_contor");
			    	  double pasContor =rs.getDouble("pas_contor");
			    	  int numberOfMachine = rs.getInt("number_of_machine");
			    	  int signalLevel = rs.getInt("signal_level");
			    	  String date = rs.getString("data_entry");
			    	    			    	    
			    	  chReg = new Location(id,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
	
			      }
				return chReg;
			}
			
			//Delete Row Location
			public void deleteRowLocation(int id,int idLocation,String monthly,String year) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DELETE FROM locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year+" WHERE id=?";
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.setInt(1, id);
				ps.executeUpdate();
				helper.closeConnection(con);
			}
			
			//DROP TableChasRegister
			public void dropChasRegisterByUser(int idLocation,String monthly,String year) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DROP TABLE IF EXISTS locationcounterslist_idLocation_"+idLocation+"__month_"+monthly+"_year_"+year;
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
		  }
			
     }
