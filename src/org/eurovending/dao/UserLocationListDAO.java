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
import org.eurovending.pojo.UsersLocationList;

public class UserLocationListDAO {

	//Creare Tabela lista locatii/user
	
	public void createTableLocationUserLocationList(int idUser) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String insertLocationList = "CREATE TABLE IF NOT EXISTS userloclist_id_"+idUser+
		" (id INTEGER not NULL auto_increment primary key,id_user INTEGER not NULL ,id_location INTEGER not NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";	
			Statement stmt = con.createStatement();		      
		       stmt.execute(insertLocationList);
		       helper.closeConnection(con);	       
		}
	
	//get Location by id if exist
	public int getUserLocationListIfExist(int idUser) throws SQLException{
	
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		UsersLocationList chReg = new UsersLocationList();
		int count = 0;
		String strSql = "SELECT * FROM userloclist_id_"+idUser;
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      try {
	             while(rs.next()){
	                count++;
	             }
	            
	        } catch (SQLException ex) {
	                
	         }    
		return count;
	}
	
	//get Location by id if exist
		public ArrayList<UsersLocationList> getUserLocationList(int idUser) throws SQLException{
			ArrayList<UsersLocationList> userLocList = new ArrayList<UsersLocationList>();
			UsersLocationList location = new UsersLocationList();
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			UsersLocationList chReg = new UsersLocationList();
			int count = 0;
			String strSql = "SELECT * FROM userloclist_id_"+idUser;
			Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery(strSql);      
		             while(rs.next()){
		            	 int id = rs.getInt("id");
				    	 int idUserTable = rs.getInt("id_user");
				    	 int idLocation = rs.getInt("id_location");
				    	 location =new UsersLocationList(id,idUserTable,idLocation);
				    	 userLocList.add(location);
		             }   
			return userLocList;
		}
	
	
	//Insert  into table LocationList
	public void insertUserLocationList(UsersLocationList location) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		boolean testLoc = false;
		String insertLocation = "insert into userloclist_id_"+location.getIdUser()+
				"(id_user,id_location)"
				  +" value(?,?)";
		for(int i = 0;i<location.getLocationName().size();i++) {			
		PreparedStatement ps = con.prepareStatement(insertLocation);
		ps.setInt(1, location.getIdUserList()[i]);
		ps.setInt(2,location.getIdLocationList()[i]);
		ps.executeUpdate();			
		}
		 helper.closeConnection(con);
	      }
	
	//DROP TableUserLocationList
		public void dropUserLocationList(int idUser) throws SQLException {
			DBHelper helper = new DBHelper();
			Connection con = helper.getConnection();
			String deleteRow = "DROP TABLE IF EXISTS userloclist_id_"+idUser;
			
			PreparedStatement ps = con.prepareStatement(deleteRow);
			ps.executeUpdate();
			helper.closeConnection(con);
			
	  }

}
