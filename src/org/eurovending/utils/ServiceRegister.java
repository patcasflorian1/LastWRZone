package org.eurovending.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.eurovending.controller.LoginController;
import org.eurovending.dao.ChasRegisterByUserDAO;
import org.eurovending.dao.ChasRegisterDAO;
import org.eurovending.dao.CompanyDataDAO;
import org.eurovending.dao.UserDAO;
import org.eurovending.pojo.ChasRegister;
import org.eurovending.pojo.ChasRegisterByUser;
import org.eurovending.pojo.CompanyData;
import org.eurovending.pojo.User;
import org.eurovending.pojo.UsersLocationList;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;



public class ServiceRegister {
public ArrayList<ChasRegister> listChasRegisters() throws SQLException {
	 ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	 ArrayList<ChasRegister> chRegList = new ArrayList<ChasRegister>();
	 chRegList = chRegDAO.getAllChasRegister();
	 return chRegList;
}

public boolean  validateChasReg(String month,String year) throws SQLException {
	boolean notExist = true;
	ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	ArrayList<ChasRegister> chasRegisters = new ArrayList<ChasRegister>();
	chasRegisters =  chRegDAO.getAllChasRegister();
	for(ChasRegister c: chasRegisters) {
		if((month.equalsIgnoreCase(c.getMonth()))&&(year.equalsIgnoreCase(c.getYear()))){
			notExist = false;
			return notExist;
		}
	}
	return notExist;
}
public boolean createChasRegisters() throws SQLException {
	LocalDate currentdate = LocalDate.now();
	Month month = currentdate.getMonth();	
   String currentYear =Integer.toString(currentdate.getYear());
  String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
	ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
	boolean notExist = validateChasReg( monthName, currentYear);
	if(notExist) {
	 ChasRegister chReg = new ChasRegister(LoginController.getMyUserName(),monthName,currentYear);
	chRegDAO.insertRegister(chReg);
	chRegByUserDAO.createTableRegister( monthName,currentYear);
	}
	return notExist;
}

public void insertAutomaticChasRegister() throws SQLException {
	LocalDate currentdate = LocalDate.now();
	Month month = currentdate.getMonth();	
   String currentYear =Integer.toString(currentdate.getYear());
  String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
	ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
	ChasRegister chasRegister = new ChasRegister();
	chasRegister = chRegDAO.getLastChasRegister();
	if((!chasRegister.getMonth().equalsIgnoreCase(monthName))||(!chasRegister.getYear().equalsIgnoreCase(currentYear))) {
		chasRegister.setMonth(monthName);
		chasRegister.setYear(currentYear);
		chRegDAO.insertRegister(chasRegister);
		chRegByUserDAO.createTableRegister(monthName, currentYear);
	}
}
public void deleteChasRegister(int id) throws SQLException {
	LocalDate currentdate = LocalDate.now();
	Month month = currentdate.getMonth();	
   String currentYear =Integer.toString(currentdate.getYear());
  String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro"));
	ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
	ChasRegister chasRegister = new ChasRegister();
	chasRegister = chRegDAO.getChasRegister(id);
	if(!chasRegister.getMonth().equalsIgnoreCase(monthName)){
	chRegDAO.deleteLineChasRegister( id);
	chRegByUserDAO.dropChasRegisterByUser(chasRegister.getMonth(), chasRegister.getYear());
	}
}

public void deleteRowChasRegister(int id) throws SQLException {
	LocalDate currentdate = LocalDate.now();
	Month month = currentdate.getMonth();	
   String currentYear =Integer.toString(currentdate.getYear());
  String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro"));
	ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
	chRegByUserDAO.deleteChasRegisterByUser(id, monthName, currentYear);
}

public String getPayUser(double encashment,double payment,String explanation) throws SQLException {
	LocalDate currentdate = LocalDate.now();
	Month month = currentdate.getMonth();	
   String currentYear =Integer.toString(currentdate.getYear());
  String monthName =month.getDisplayName( TextStyle.FULL_STANDALONE , Locale.forLanguageTag("ro") );
       // TotalPaymentUser totPay = new TotalPaymentUser();
	    ChasRegisterDAO chRegDAO = new ChasRegisterDAO();
	    ChasRegisterByUserDAO chRegByUserDAO = new ChasRegisterByUserDAO();
	  // TotalPaymentUserDAO totPdao = new TotalPaymentUserDAO();
		UserDAO udao = new UserDAO();
		User operatorUser = new User();
		User userPayer = new User();
	 ChasRegister chReg = new ChasRegister();
	 ChasRegisterByUser chRegByUser = new ChasRegisterByUser();
	String msg = null;
		  	
	 int countChReg = chRegDAO.verifyTableRegister();
	 int countChRegByUser = chRegByUserDAO.verifyTableRegister( monthName, currentYear);
	 operatorUser = udao.getIdUser(LoginController.getIdUser());
	 if((countChReg == 0)&&(countChRegByUser == 0)) {
		 chRegByUser.setPreviousBalance(0);
		
	 }
	 else {
		 if((countChReg >= 0)&&(countChRegByUser == 0)) {
			 chReg = chRegDAO.getTwoLastChasRegister();
			 chRegByUser =chRegByUserDAO.getLastPay(chReg.getMonth(), chReg.getYear());
			 chRegByUser.setPreviousBalance(chRegByUser.getFinalBalance());
		 }else {
		 chRegByUser =chRegByUserDAO.getLastPay( monthName, currentYear);
		 chRegByUser.setPreviousBalance(chRegByUser.getFinalBalance());
		 }
	 }
	 chRegByUser.setEncashment(encashment);
	 chRegByUser.setPayment(payment);
	 chRegByUser.setFinalBalance(chRegByUser.getPreviousBalance()+encashment-payment);
	 chRegByUser.setOperator(operatorUser.getFullName());
	 chRegByUser.setExplanation(explanation);
	 chRegByUserDAO.insertRegister(chRegByUser, monthName, currentYear);
    return msg;
   }
/*
public TaxCollection userTaxCollect (int userId ,UsersLocationList userLocList,UserListOfExpenses userListExpens) throws SQLException{
	int id = 1;
	TaxCollection taxCollection = new TaxCollection();
	CompanyData compData = new CompanyData();
	CompanyDataDAO compDAO = new CompanyDataDAO();
	UserProfile userProfile = new UserProfile();
	UserProfileDAO userProfDAO = new UserProfileDAO();
	userProfile = userProfDAO.getIdUserProfile(userId);
	double totalMachineUser = userProfile.getNrAwp()+userProfile.getNrSlotMachine();
	compData = compDAO.getCompanyData(id);
	double totIncasare = 0;
	double totExpenses = 0;
	for(int i =0;i<userLocList.getLocationList().size();i++) {
		totIncasare = userLocList.getEncashementList()[i]+totIncasare;
	}
	for(int i = 0;i<userListExpens.getExpensesList().size();i++) {
		totExpenses = userListExpens.getPayList()[i]+ totExpenses;
	}
	taxCollection.setTotalCollected(totIncasare);
	taxCollection.setTotalPayment(totExpenses);
	taxCollection.setImpozitTaxVal((totIncasare*compData.getImpozitTax())/100);
	taxCollection.setImpozitVenitVal(((totIncasare-totExpenses)*compData.getImpozitVenit())/100);
	double impozitDividenteVal =(((totIncasare-totExpenses -taxCollection.getImpozitVenitVal())*compData.getImpozitDividente())/100);
	 
	taxCollection.setImpozitDividenteVal(impozitDividenteVal);
	taxCollection.setImpozitCASSVal((compData.getImpozitCASS()/compData.getTotMachineLicensing())*totalMachineUser);
	taxCollection.setPaymentTaxes(impozitDividenteVal+taxCollection.getImpozitTaxVal()
	+taxCollection.getImpozitVenitVal()+taxCollection.getImpozitCASSVal());
	taxCollection.setIdUser(userId);
	taxCollection.setDataEntry(userLocList.getMonthYear());
	return taxCollection;
}
*/
}
