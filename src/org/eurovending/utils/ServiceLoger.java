package org.eurovending.utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eurovending.controller.LoginController;
import org.eurovending.dao.ChasRegisterDAO;
import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.dao.LocationListRegisterDAO;
import org.eurovending.dao.LocationMonthYearDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.Location;

import org.eurovending.pojo.User;


public class ServiceLoger {
	//String userNameSystem = System.getProperty("user.name");
	public UserDAO udao = new UserDAO();
    public LocationDAO locdao = new LocationDAO();
	public LocationListRegisterDAO locListRegDAO = new LocationListRegisterDAO();
	public ChasRegisterDAO chdao = new ChasRegisterDAO();
	public CompanyDataDAO compdao = new CompanyDataDAO();
	public  PasswordUtils uPassword = new PasswordUtils();
	public ArrayList<User> userList = new ArrayList<User>();	
	public String salt = null ;
	public String adminPassword = "eurovending";
	public String generatePassword = null;
	public String serialNr = "811504";
	//generate tabel if not exist
	public void generateTable() {
	 salt = uPassword.getSalt(30);
	 generatePassword = uPassword.generateSecurePassword( adminPassword,salt);
	 User superAdmin = new User("Patcas Florin","+40743556569",
             "patcasf12@gmail.com",salt,generatePassword,"SUPERADMIN","ACTIVE","Florin","22.01.2024");

	 try {
			udao.createTableUser();
			locdao.createTableLocationList();
			locListRegDAO.createTableLocationListRegisters();
			insertLocationListMonthYear();
			compdao.createTableCompanyData();
			chdao.createTableRegister();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int count=0;
		try {
			 
			 count = udao.verifyTableUser();
			 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(count==0) {
			   try {
				udao.insertUser(superAdmin);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		 }	
	}
	
	//__end generate table if not exist__

	//test user&password is not empty
	public boolean testLogin(String user,String pwd) throws SQLException {
		boolean isOkUser  = false;
		boolean myEmailIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(user) ;
		boolean myPasswordIsOk = LoginController.isNotNullNotEmptyNotWhiteSpace(pwd);
		userList = udao.getUserList();
		if((myEmailIsOk==true)&&(myPasswordIsOk==true)) {
			isOkUser  = true;
			
			return isOkUser ;
		}	
		else {
			return isOkUser;
			}
		}
	
	
	
	public void insertLocationListMonthYear() throws SQLException {
		LocalDate currentdate = LocalDate.now();
		Month currentMonth = currentdate.getMonth();	
	   String year =Integer.toString(currentdate.getYear());
	  String month =currentMonth.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
	  
		LocationListRegisterDAO locListRegDAO = new LocationListRegisterDAO();
		LocationMonthYearDAO locMonthYearDAO = new LocationMonthYearDAO();
		ArrayList<Location> locationList = new ArrayList<Location>();
		LocationDAO locDAO = new LocationDAO();
		Location location = new Location();
		int eptyLocListRegDAO = locListRegDAO.verifyTableLocationListRegisters();
		
		if(eptyLocListRegDAO > 0) {
		location = locListRegDAO.getLastLocationListRegisters();
		}
		testLocationListReg(month,year);
		if(!location.getMonth().equalsIgnoreCase(month)){
			locationList = locDAO.getAllLocation();
			locMonthYearDAO.createTableLocationList(location.getMonth(), location.getYear());
			 eptyLocListRegDAO = locMonthYearDAO.verifyTableRegister(location.getMonth(), location.getYear());
			if(eptyLocListRegDAO == 0) {
			locMonthYearDAO.insertLocation(locationList, location.getMonth(), location.getYear());
			}
		}
	}
	
	public void testLocationListReg( String month,String year) throws SQLException {	
		LocationListRegisterDAO locListRegDAO = new LocationListRegisterDAO();
		//LocationMonthYearDAO locMonthYearDAO = new LocationMonthYearDAO();
		ArrayList<Location> locationList = new ArrayList<Location>();		
		int eptyLocListRegDAO = locListRegDAO.verifyTableLocationListRegisters();
		if(eptyLocListRegDAO == 0) {
			locListRegDAO.insertLocationListRegisters(month, year);		
		}else {	
			locationList = locListRegDAO.getAllLocationListRegisters();	
		for(Location l:locationList) {			
			if((l.getMonth().equalsIgnoreCase(month))&&(l.getYear().equalsIgnoreCase(year))) {
				return;
			}
		 }			
			locListRegDAO.insertLocationListRegisters(month, year);		
		}
	}

}
