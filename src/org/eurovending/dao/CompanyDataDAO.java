package org.eurovending.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.eurovending.helper.DBHelper;
import org.eurovending.pojo.CompanyData;

public class CompanyDataDAO {

	//Creare Tabela cu Date companie
			public void createTableCompanyData() throws SQLException {
					DBHelper helper = new DBHelper();
					Connection con = helper.getConnection();
					String insertCompanyData = "CREATE TABLE IF NOT EXISTS company_data"+ 
							" (id INTEGER not NULL auto_increment primary key,denumire VARCHAR(150) NULL,nr_onrc VARCHAR(15) NULL,"
							+ "cui VARCHAR(50) NULL,adress VARCHAR(500) NULL,"
		                    +"data_entry DATETIME NULL )ENGINE=InnoDB COLLATE=utf8mb4_romanian_ci CHARSET=utf8mb4 MAX_ROWS = 2000";
							
							
				      Statement stmt = con.createStatement();
				      
				       stmt.execute(insertCompanyData);
				       helper.closeConnection(con);
				       
				}
			
			
			//verify if table CompanyData is Empty
			
			public int verifyCompanyData() throws SQLException {
				
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String companyData = "SELECT * FROM company_data";
				int count = 0;	
			    Statement stmt = (PreparedStatement) con.prepareStatement(companyData);
			    ResultSet rst = stmt.executeQuery(companyData);
			        try {
			             while(rst.next()){
			                count++;
			             }
			            
			        } catch (SQLException ex) {
			                
			         }
			  
			     helper.closeConnection(con);
			     return count;
			}
			

			//Insert  into table Company Data
			public void insertCompanyData(CompanyData data) throws SQLException {
				java.util.Date date=new java.util.Date();
				java.sql.Timestamp sqlDate=new java.sql.Timestamp(new java.util.Date().getTime());
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String insertCompanyData = "insert into company_data "+ 
						"(denumire,nr_onrc,cui,adress,data_entry)"
						  +" value(?,?,?,?,?)";
				
				PreparedStatement ps = con.prepareStatement(insertCompanyData);
				ps.setString(1, data.getDenumire());
				ps.setString(2,data.getNrONRC());
				ps.setString(3, data.getCui());
				ps.setString(4, data.getAdress());
				ps.setString(5, sqlDate.toString());
				ps.executeUpdate();
				
				 helper.closeConnection(con);
			      }

			//afisare continut tabela Company Data
			public ArrayList<CompanyData> getCompanyDataList() throws SQLException{
				ArrayList<CompanyData> companyList=new ArrayList<CompanyData>();
				CompanyData company = null;
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String getCompany = "select*from company_data";
			      Statement stmt = con.createStatement();
			      ResultSet rst = stmt.executeQuery(getCompany);
			      while(rst.next()) {
			    	  
			    	     int id = rst.getInt("id");
			    		 String denumire = rst.getString("denumire");
			    		 String nrONRC = rst.getString("nr_onrc");
			    		 String cui = rst.getString("cui");
			    		 String adress = rst.getString("adress");
			    	     Date date = rst.getTimestamp("data_entry");
				    	    
			    	     SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.ENGLISH);
			    	     String dataEntry =  formatter.format(date);
			    	    
			 
			    	 company = new CompanyData(id,denumire,nrONRC,cui,adress,dataEntry);
			    	 companyList.add(company);
			      }
			      helper.closeConnection(con);
					return companyList;
			}
			
			//afisare first  Company Data
			public CompanyData getCompanyData(int id) throws SQLException{
				//ArrayList<CompanyData> companyList=new ArrayList<CompanyData>();
				CompanyData company = null;
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String getCompany = "select*from company_data where id='"+id+"'";
			      Statement stmt = con.createStatement();
			      ResultSet rst = stmt.executeQuery(getCompany);
			      while(rst.next()) {
			    	  
			    	    // int id = rst.getInt("id");
			    		 String denumire = rst.getString("denumire");
			    		 String nrONRC = rst.getString("nr_onrc");
			    		 String cui = rst.getString("cui");
			    		 String adress = rst.getString("adress");
			    	     Date date = rst.getTimestamp("data_entry");
				    	    
			    	     SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.ENGLISH);
			    	     String dataEntry =  formatter.format(date);
			    	    
			 
			    	 company = new CompanyData(id,denumire,nrONRC,cui,adress,dataEntry);
			    	// companyList.add(company);
			      }
			      helper.closeConnection(con);
					return company;
			}
			
			//Update Company data
			public void updateCompanyData(CompanyData companyData) throws SQLException {
				java.util.Date date=new java.util.Date();
				java.sql.Timestamp sqlDate=new java.sql.Timestamp(new java.util.Date().getTime());
				DBHelper helper = new DBHelper();
				Connection con = helper.getConnection();
				String updateCompanyData = "UPDATE company_data SET denumire  = '"+companyData.getDenumire()+"',nr_onrc = '"+companyData.getNrONRC()+"',cui = '"
				+companyData.getCui()+ "',adress = '"+companyData.getAdress()+"',data_entry ='"+sqlDate.toString()+"' where id = '"+companyData.getId()+"'";
				PreparedStatement ps = con.prepareStatement(updateCompanyData);
				ps.executeUpdate();
				helper.closeConnection(con);
				
			}
			
			}
