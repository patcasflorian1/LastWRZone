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

public class LocationMonthYearDAO {

	
	public void createTableLocationList(String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS locationlist__month_"+monthly+"_year_"+year
				+" (id INTEGER not NULL auto_increment primary key,location_name VARCHAR(250) NOT NULL,location_adress VARCHAR(550) NULL,mac_location VARCHAR(50) NOT NULL,permanent_contor INTEGER NULL,month_contor DECIMAL(10,2) NULL,day_contor DECIMAL(10,2) NULL,pas_contor DECIMAL(10,2) NULL,number_of_machine INTEGER NULL,signal_level INTEGER NULL,data_entry VARCHAR(50) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}
	//verify if table Register is Empty
		public int verifyTableRegister(String monthly,String year) throws SQLException {
			
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String selectUser = "SELECT * FROM locationlist__month_"+monthly+"_year_"+year;
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
		
		//Insert  into table Location
		public void insertLocation(ArrayList<Location> location,String monthly,String year) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
			String paymentDate =zonedDateTime.toString();
			
			 String regex = "[T.]";
			String myArray[] = paymentDate.split(regex); 
			 paymentDate = myArray[0]+" "+myArray[1];
			 for(Location l:location) {
			String insertLocation = "insert into locationlist__month_"+monthly+"_year_"+year
					+"( location_name,location_adress,mac_location,permanent_contor,month_contor,day_contor,pas_contor,number_of_machine,signal_level,data_entry)"
					  +" value(?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(insertLocation);
			//ps.setInt(1, location.getId());
			ps.setString(1, l.getLocationName());
			ps.setString(2,l.getLocationAdress());
			ps.setString(3,l.getLocationMacAddress());
			ps.setDouble(4, l.getPermanentContor());
			ps.setDouble(5, l.getMonthContor());
			ps.setDouble(6, l.getDayContor());
			ps.setDouble(7, l.getPasContor());
			ps.setInt(8, l.getNumberOfMachine());
			ps.setInt(9, l.getSignalLevel());
			ps.setString(10, paymentDate);
			ps.executeUpdate();
			 }
			 helper.closeConnection(con);
		      }
		
		//Insert  into table LocationList
				public void insertLocationList(Location location) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
					String paymentDate =zonedDateTime.toString();
					
					 String regex = "[T.]";
					String myArray[] = paymentDate.split(regex); 
					 paymentDate = myArray[0]+" "+myArray[1];
					String insertLocation = "insert into locationlist__month_"+location.getMonth()+"_year_"+location.getYear()
							+"( location_name,location_adress,mac_location,permanent_contor,month_contor,day_contor,pas_contor,number_of_machine,signal_level,data_entry)"
							  +" value(?,?,?,?,?,?,?,?,?,?)";
					
					PreparedStatement ps = con.prepareStatement(insertLocation);
					//ps.setInt(1, location.getId());
					ps.setString(1, location.getLocationName());
					ps.setString(2,location.getLocationAdress());
					ps.setString(3,location.getLocationMacAddress());
					ps.setDouble(4, location.getPermanentContor());
					ps.setDouble(5, location.getMonthContor());
					ps.setDouble(6, location.getDayContor());
					ps.setDouble(7, location.getPasContor());
					ps.setInt(8, location.getNumberOfMachine());
					ps.setInt(9, location.getSignalLevel());
					ps.setString(10, paymentDate);
					ps.executeUpdate();
					
					 helper.closeConnection(con);
				      }
				
				//get allLocationList
				public ArrayList<Location> getAllLocation(String monthly,String year) throws SQLException{
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location chReg = new Location();
					 ArrayList<Location> chRegList = new ArrayList<Location>();
					String strSql = ("SELECT * FROM locationlist__month_"+monthly+"_year_"+year);
					Statement stmt = con.createStatement();
				      ResultSet rs = stmt.executeQuery(strSql);
				      while(rs.next()) {
				    	  int id = rs.getInt("id");
				    	  String locationName = rs.getString("location_name");
				    	  String locationAdress = rs.getString("location_adress");
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
				    	  chReg = new Location(id,locationName,locationAdress,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
				    
				    	  chRegList.add(chReg);
				      }
					return chRegList;
				}
				
				//Update table LocationName
				public void updateLocationName(Location location) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					System.out.println("Cont "+location.getPasContor());
					String updateLocationList = "UPDATE locationlist__month_"+location.getMonth()+"_year_"+location.getYear()+
							" SET location_name = '"+location.getLocationName()+"',location_adress = '"+location.getLocationAdress()+"',number_of_machine = '"+location.getNumberOfMachine()+"',pas_contor = '"+location.getPasContor()+"' where id = '"+location.getId()+"'";
							 
					PreparedStatement ps = con.prepareStatement(updateLocationList);
					ps.executeUpdate();		
					 helper.closeConnection(con);
				      }
				
				//Update table LocationContor by MAC ADDRESS
				public void updateLocationContor(Location location) throws SQLException {
					
					LocalDateTime now = LocalDateTime.now();
					ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
					String paymentDate =zonedDateTime.toString();
					 String regex = "[T.]";
					String myArray[] = paymentDate.split(regex); 
					 paymentDate = myArray[0]+" "+myArray[1];
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String updateLocationList = "UPDATE locationlist__month_"+location.getMonth()+"_year_"+location.getYear()
					+" SET permanent_contor = '"+location.getPermanentContor()+"',month_contor = '"+location.getMonthContor()+"'"
					+ ",day_contor = '"+location.getDayContor()+"',pas_contor = '"+location.getPasContor()+"',signal_level = '"+location.getSignalLevel()+"',data_entry = '"+paymentDate+"'"
					+ " where mac_location = '"+location.getLocationMacAddress()+"'";
							 
					PreparedStatement ps = con.prepareStatement(updateLocationList);
					ps.executeUpdate();		
					 helper.closeConnection(con);
				      }
				
				//Update table LocationTime by MAC ADDRESS
				public void updateLocationTime(Location location) throws SQLException {
					
					LocalDateTime now = LocalDateTime.now();
					ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
					String paymentDate =zonedDateTime.toString();
					 String regex = "[T.]";
					String myArray[] = paymentDate.split(regex); 
					 paymentDate = myArray[0]+" "+myArray[1];
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String updateLocationList = "UPDATE locationlist__month_"+location.getMonth()+"_year_"+location.getYear()
					+" SET permanent_contor = '"+location.getPermanentContor()+"',signal_level = '"+location.getSignalLevel()+"',data_entry = '"+paymentDate+"'"
					+ " where mac_location = '"+location.getLocationMacAddress()+"'";			 
					PreparedStatement ps = con.prepareStatement(updateLocationList);
					ps.executeUpdate();		
					 helper.closeConnection(con);
				      } 
				
				//get Location by name
				public Location getLocationByName(String locationName,String monthly,String year) throws SQLException{
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location chReg = new Location();
					String strSql = ("SELECT * FROM locationlist__month_"+monthly+"_year_"+year+" where location_name = '"+locationName+"'");
					Statement stmt = con.createStatement();
				      ResultSet rs = stmt.executeQuery(strSql);
				      while(rs.next()) {
				    	  int id = rs.getInt("id");
				    	 // String locationName = rs.getString("location_name");
				    	  String locationAdress = rs.getString("location_adress");
				    	  String macAddress = rs.getString("mac_location");
				    	  double permanentContor = rs.getDouble("permanent_contor");
				    	  double monthContor = rs.getDouble("month_contor");
				    	  double dayContor =rs.getDouble("day_contor");
				    	  double pasContor =rs.getDouble("pas_contor");
				    	  int numberOfMachine = rs.getInt("number_of_machine");
				    	  int signalLevel = rs.getInt("signal_level");
				    	  String date = rs.getString("data_entry");
				    	    			    	    
				    	  chReg = new Location(id,locationName,locationAdress,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
		
				      }
					return chReg;
				}
				
				//get Location by id
				public Location getLocation(int idLocation,String monthly,String year) throws SQLException{
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location chReg = new Location();
					String strSql = ("SELECT * FROM locationlist__month_"+monthly+"_year_"+year+" where id = "+idLocation);
					Statement stmt = con.createStatement();
				      ResultSet rs = stmt.executeQuery(strSql);
				      while(rs.next()) {
				    	  int id = rs.getInt("id");
				    	  String locationName = rs.getString("location_name");
				    	  String locationAdress = rs.getString("location_adress");
				    	  String macAddress = rs.getString("mac_location");
				    	  double permanentContor = rs.getDouble("permanent_contor");
				    	  double monthContor = rs.getDouble("month_contor");
				    	  double dayContor =rs.getDouble("day_contor");
				    	  double pasContor =rs.getDouble("pas_contor");
				    	  int numberOfMachine = rs.getInt("number_of_machine");
				    	  int signalLevel = rs.getInt("signal_level");
				    	  String date = rs.getString("data_entry");
				    	    			    	    
				    	  chReg = new Location(id,locationName,locationAdress,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
		
				      }
					return chReg;
				}
				
				//get Location by MacAddress
				public Location getLocationByMacAddr(String macAddres,String monthly,String year) throws SQLException{
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location chReg = new Location();
					String strSql = ("SELECT * FROM locationlist__month_"+monthly+"_year_"+year+" where mac_location = "+macAddres);
					Statement stmt = con.createStatement();
				      ResultSet rs = stmt.executeQuery(strSql);
				      while(rs.next()) {
				    	  int id = rs.getInt("id");
				    	  String locationName = rs.getString("location_name");
				    	  String locationAdress = rs.getString("location_adress");
				    	  String macAddress = rs.getString("mac_location");
				    	  double permanentContor = rs.getDouble("permanent_contor");
				    	  double monthContor = rs.getDouble("month_contor");
				    	  double dayContor =rs.getDouble("day_contor");
				    	  double pasContor =rs.getDouble("pas_contor");
				    	  int numberOfMachine = rs.getInt("number_of_machine");
				    	  int signalLevel = rs.getInt("signal_level");
				    	  String date = rs.getString("data_entry");
				    	    
				    	  chReg = new Location(id,locationName,locationAdress,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,signalLevel,date);
				    	  	
				      }
					return chReg;
				}

				//Delete Row Location
				public void deleteRowLocation(int id,String monthly,String year) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DELETE FROM locationlist__month_"+monthly+"_year_"+year+" WHERE id=?";
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.setInt(1, id);
					ps.executeUpdate();
					helper.closeConnection(con);
				}
				
				//DROP TableChasRegister
				public void dropChasRegisterByUser(String monthly,String year) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DROP TABLE IF EXISTS locationlist__month_"+monthly+"_year_"+year;
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					helper.closeConnection(con);
					
			  }
}
