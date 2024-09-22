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

public class AccountLocationDAO {

	public void createTableAccountLocation(int idLocation) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertLocationList = "CREATE TABLE IF NOT EXISTS accountlocation"+idLocation+
				" (id INTEGER not NULL auto_increment primary key,mac_location VARCHAR(50) NOT NULL,"
				+ "permanent_contor INTEGER NULL,month_contor DECIMAL(10,2) NULL,day_contor DECIMAL(10,2) NULL,"
				+ "pas_contor DECIMAL(10,2) NULL,number_of_machine INTEGER NULL,operator VARCHAR(100) NULL,"
				+ "data_entry VARCHAR(50) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
		Statement stmt = con.createStatement();		      
	       stmt.execute(insertLocationList);
	       helper.closeConnection(con);	       
	}

	//verify if table Register is Empty
			public int verifyTableAccountLocation(int idLocation) throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String selectUser = "SELECT * FROM accountlocation"+idLocation;
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
	public void insertAccountLocation(Location location,int idLocation) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		//DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String paymentDate =zonedDateTime.toString();
		
		 String regex = "[T.]";
		String myArray[] = paymentDate.split(regex); 
		 paymentDate = myArray[0]+" "+myArray[1];
		String insertLocation = "insert into accountlocation"+idLocation+
				"( mac_location,permanent_contor,month_contor,day_contor,pas_contor,number_of_machine,operator,data_entry)"
				  +" value(?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(insertLocation);
		ps.setString(1,location.getLocationMacAddress());
		ps.setDouble(2, location.getPermanentContor());
		ps.setDouble(3, location.getMonthContor());
		ps.setDouble(4, location.getDayContor());
		ps.setDouble(5, location.getPasContor());
		ps.setInt(6, location.getNumberOfMachine());
		ps.setString(7, location.getOperator());
		ps.setString(8, paymentDate);
		ps.executeUpdate();
		
		 helper.closeConnection(con);
	      }
	

		//Update table LocationTime by MAC ADDRESS
		public void updateAccountLocation(Location location,int idLocation) throws SQLException {

			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String updateLocationList = "UPDATE accountlocation"+idLocation
			+" SET month_contor = '"+location.getMonthContor()+"',day_contor = '"+location.getDayContor()+"'"
			+ " where id = '"+location.getId()+"'";			 
			PreparedStatement ps = con.prepareStatement(updateLocationList);
			ps.executeUpdate();		
			 helper.closeConnection(con);
		      } 
		//get allLocationList
		public ArrayList<Location> getAllAccountLocation(int idLocation) throws SQLException{
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			Location chReg = new Location();
			 ArrayList<Location> chRegList = new ArrayList<Location>();
			String strSql = "SELECT * FROM accountlocation"+idLocation;
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
		    	  String operator = rs.getString("operator");
		    	  String date = rs.getString("data_entry");
		    	    
		    	    // SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
		    	     //String dataEntry =  formatter.format(date);
		    	  chReg = new Location(id,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,operator,date);
		    
		    	  chRegList.add(chReg);
		      }
			return chRegList;
		}
		
		//get Location by id
		public Location getAccountLocation(int id,int idLocation) throws SQLException{
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			Location chReg = new Location();
			String strSql = "SELECT * FROM accountlocation"+idLocation+" where id = "+id;
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(strSql);
		      while(rs.next()) {
		    	  String macAddress = rs.getString("mac_location");
		    	  double permanentContor = rs.getDouble("permanent_contor");
		    	  double monthContor = rs.getDouble("month_contor");
		    	  double dayContor =rs.getDouble("day_contor");
		    	  double pasContor =rs.getDouble("pas_contor");
		    	  int numberOfMachine = rs.getInt("number_of_machine");
		    	  String operator = rs.getString("operator");
		    	  String date = rs.getString("data_entry");
		    	    			    	    
		    	  chReg = new Location(id,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,operator,date);

		      }
			return chReg;
		}
		
		//get last ChasRegister
				public Location getLastAccountLocation(int idLocation) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					Location loc = new Location();
					
					String strSql = ("SELECT * FROM accountlocation"+idLocation+ " ORDER BY id DESC LIMIT 1");
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
				    	  String operator = rs.getString("operator");
				    	  String date = rs.getString("data_entry");
				    	    			    	    
				    	  loc = new Location(id,macAddress,permanentContor,monthContor,dayContor,pasContor,numberOfMachine,operator,date);
				      }
					return loc;
				}
				
				//Delete LineChasRegister
				public void deleteLineLastAccountLocation(int id,int idLocation) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DELETE FROM accountlocation"+idLocation+" WHERE id='"+id+"'";
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					helper.closeConnection(con);
					
	          }	
				
				//DROP TableChasRegister
				public void dropLastAccountLocation(int idLocation) throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String deleteRow = "DROP TABLE IF EXISTS accountlocation"+idLocation;
					
					PreparedStatement ps = con.prepareStatement(deleteRow);
					ps.executeUpdate();
					helper.closeConnection(con);
					
	          }
}
