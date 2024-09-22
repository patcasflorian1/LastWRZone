package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.ChasRegisterByUser;

public class ChasRegisterByUserDAO {
	
	//Creare Tabela Pati by User
	public void createTableRegister(String monthly,String year) throws SQLException {
			DBHelper helper = new DBHelper();
			LocalDate currentdate = LocalDate.now();
			 Month currentMonth = currentdate.getMonth();
			String month = String.valueOf(currentMonth);
			Connection con = helper.getConnection();
			String insertUser = "CREATE TABLE IF NOT EXISTS chas_register_month_"+monthly+"_year_"+year+
					" (id INTEGER not NULL auto_increment primary key,operator VARCHAR(250) not NULL,"
					+ "previous_balance DECIMAL(10,2) NULL,encashment DECIMAL(10,2) NULL,"
                    + "payment DECIMAL(10,2) NULL,final_balance DECIMAL(10,2) NULL,explanation VARCHAR(250) NULL,payment_date DATETIME NULL) ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 4000";
					
		      Statement stmt = con.createStatement();
		      
		       stmt.execute(insertUser);
		       helper.closeConnection(con);
		       
		}
	
	//verify if table Register is Empty
	public int verifyTableRegister(String monthly,String year) throws SQLException {
		
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String selectUser = "SELECT * FROM chas_register_month_"+monthly+"_year_"+year;
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
	public void insertRegister(ChasRegisterByUser chreg,String monthly,String year) throws SQLException {
		
		java.util.Date date=new java.util.Date();
		java.sql.Timestamp sqlDate=new java.sql.Timestamp(new java.util.Date().getTime());
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String insertPay = "insert into chas_register_month_"+monthly+"_year_"+year+ 
				"( operator,previous_balance,encashment,payment,final_balance,explanation,payment_date)"
				  +" value(?,?,?,?,?,?,?)";
		
		PreparedStatement ps = con.prepareStatement(insertPay);
		
		ps.setString(1,chreg.getOperator());
		ps.setDouble(2, chreg.getPreviousBalance());
		ps.setDouble(3, chreg.getEncashment());
		ps.setDouble(4, chreg.getPayment());
		ps.setDouble(5, chreg.getFinalBalance());
		ps.setString(6, chreg.getExplanation());
		ps.setString(7, sqlDate.toString());
		ps.executeUpdate();
		
		 helper.closeConnection(con);
	      }
	//get last payment
	public ChasRegisterByUser getLastPay(String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		 ChasRegisterByUser chReg = new ChasRegisterByUser();
		
		String strSql = ("SELECT * FROM chas_register_month_"+monthly+"_year_"+year+" ORDER BY id DESC LIMIT 1");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	
	    	  String operator = rs.getString("operator");
	    	  double prevBalance = rs.getDouble("previous_balance");
	    	  double encashment = rs.getDouble("encashment");
	    	  double payment = rs.getDouble("payment");
	    	  double finalBalance = rs.getDouble("final_balance");
	    	  String explanation = rs.getString("explanation");
	    	  String datePay = rs.getString("payment_date");
	    	  chReg = new ChasRegisterByUser(id,operator,prevBalance,encashment,payment,finalBalance,explanation,datePay);
	      }
		return chReg;
	}
	
	//get all payment
	public ArrayList<ChasRegisterByUser> getAllPay(String monthly,String year) throws SQLException{
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		ChasRegisterByUser chReg = new ChasRegisterByUser();
		 ArrayList<ChasRegisterByUser> chRegList = new ArrayList<ChasRegisterByUser>();
		String strSql = ("SELECT * FROM chas_register_month_"+monthly+"_year_"+year+"");
		Statement stmt = con.createStatement();
	      ResultSet rs = stmt.executeQuery(strSql);
	      while(rs.next()) {
	    	  int id = rs.getInt("id");
	    	 
	    	  String operator = rs.getString("operator");
	    	  double prevBalance = rs.getDouble("previous_balance");
	    	  double encashment = rs.getDouble("encashment");
	    	  double payment = rs.getDouble("payment");
	    	  double finalBalance = rs.getDouble("final_balance");
	    	  String explanation = rs.getString("explanation");
	    	  String datePay = rs.getString("payment_date");
	    	  chReg = new ChasRegisterByUser(id,operator,prevBalance,encashment,payment,finalBalance,explanation,datePay);
	      chRegList.add(chReg);
	      }
		return chRegList;
	}
	
	//Delete LineChasRegister
	public void deleteChasRegisterByUser(int id,String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String deleteRow = "DELETE FROM chas_register_month_"+monthly+"_year_"+year+" WHERE id='"+id+"'";
		
		PreparedStatement ps = con.prepareStatement(deleteRow);
		ps.executeUpdate();
		helper.closeConnection(con);
		
  }
	//DROP TableChasRegister
	public void dropChasRegisterByUser(String monthly,String year) throws SQLException {
		DBHelper helper = new DBHelper();
		Connection con = helper.getConnection();
		String deleteRow = "DROP TABLE IF EXISTS chas_register_month_"+monthly+"_year_"+year+"";
		
		PreparedStatement ps = con.prepareStatement(deleteRow);
		ps.executeUpdate();
		helper.closeConnection(con);
		
  }
	

}
