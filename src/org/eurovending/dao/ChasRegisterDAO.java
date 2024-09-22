package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.ChasRegister;


public class ChasRegisterDAO {
	
	//Creare Tabela Pati
			public void createTableRegister() throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String insertUser = "CREATE TABLE IF NOT EXISTS chas_registers"+
							" (id INTEGER not NULL auto_increment primary key,operator VARCHAR(250) not NULL,"
		                    + "month VARCHAR(250)  NOT NULL,year VARCHAR(250) NOT NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";
							
				      Statement stmt = con.createStatement();
				      
				       stmt.execute(insertUser);
				       helper.closeConnection(con);
				       
				}
			
			//verify if table Register is Empty
			public int verifyTableRegister() throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String selectUser = "SELECT * FROM chas_registers";
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
			public void insertRegister(ChasRegister chreg) throws SQLException {
				
				java.util.Date date=new java.util.Date();
				java.sql.Timestamp sqlDate=new java.sql.Timestamp(new java.util.Date().getTime());
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String insertPay = "insert into chas_registers"+ 
						"( operator,month,year)"
						  +" value(?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertPay);
				ps.setString(1,chreg.getOperator());
				ps.setString(2, chreg.getMonth());
				ps.setString(3, chreg.getYear());
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }
			//get last ChasRegister
			public ChasRegister getLastChasRegister() throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				 ChasRegister chReg = new ChasRegister();
				
				String strSql = ("SELECT * FROM chas_registers ORDER BY id DESC LIMIT 1");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String operator = rs.getString("operator");
			    	  String monthly = rs.getString("month");
			    	  String year = rs.getString("year");
			    	  chReg = new ChasRegister(id,operator,monthly,year);
			      }
				return chReg;
			}
			
			//get Two last ChasRegister
			public ChasRegister getTwoLastChasRegister() throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				 ChasRegister chReg = new ChasRegister();
				
				String strSql = ("SELECT * FROM chas_registers ORDER BY id DESC LIMIT 2");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	  String operator = rs.getString("operator");
			    	  String monthly = rs.getString("month");
			    	  String year = rs.getString("year");
			    	  chReg = new ChasRegister(id,operator,monthly,year);
			      }
				return chReg;
			}
			//get  ChasRegister
			public ChasRegister getChasRegister(int id) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				 ChasRegister chReg = new ChasRegister();
				
				String strSql = ("SELECT * FROM chas_registers WHERE id='"+id+"'");
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  String operator = rs.getString("operator");
			    	  String monthly = rs.getString("month");
			    	  String year = rs.getString("year");
			    	  chReg = new ChasRegister(id,operator,monthly,year);
			      }
				return chReg;
			}
			
			
			//get all payment
			public ArrayList<ChasRegister> getAllChasRegister() throws SQLException{
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				 ChasRegister chReg = new ChasRegister();
				 ArrayList<ChasRegister> chRegList = new ArrayList<ChasRegister>();
				String strSql = "SELECT * FROM chas_registers";
				Statement stmt = con.createStatement();
			      ResultSet rs = stmt.executeQuery(strSql);
			      while(rs.next()) {
			    	  int id = rs.getInt("id");
			    	 
			    	  String operator = rs.getString("operator");
			    	  String monthly = rs.getString("month");
			    	  String year = rs.getString("year");
			    	  chReg = new ChasRegister(id,operator,monthly,year);
			      chRegList.add(chReg);
			      }
				return chRegList;
			}
			
			//Delete LineChasRegister
			public void deleteLineChasRegister(int id) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DELETE FROM chas_registers WHERE id="+id;
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
          }
			//DROP TableChasRegister
			public void dropChasRegister(int idUser) throws SQLException {
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String deleteRow = "DROP TABLE IF EXISTS chas_registers";
				
				PreparedStatement ps = con.prepareStatement(deleteRow);
				ps.executeUpdate();
				helper.closeConnection(con);
				
          }
}
