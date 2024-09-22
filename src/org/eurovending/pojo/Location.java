package org.eurovending.pojo;

import java.util.ArrayList;

public class Location {
	private int  id;
	private int idLocation;
	private String locationName;
	private String locationAdress;
	private String locationMacAddress;	
	private String month;
	private String year;
	private double permanentContor;
	private double monthContor;
	private double dayContor;
	private double pasContor;
	private int numberOfMachine;
	private int signalLevel;
	private String operator;
	private String isLogin;
	private String paymentDate;
	private String onlineDate;
	
	
	
	


	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Location(String month, String year) {
		super();
		this.month = month;
		this.year = year;
	}



	public Location(int id, String month, String year) {
		super();
		this.id = id;
		this.month = month;
		this.year = year;
	}

	


	public Location(int id, int idLocation, String locationMacAddress, double permanentContor, 
			       double monthContor,double dayContor, double pasContor, int numberOfMachine,
			String operator, String paymentDate) {
		super();
		this.id = id;
		this.idLocation = idLocation;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.pasContor = pasContor;
		this.numberOfMachine = numberOfMachine;
		this.operator = operator;
		this.paymentDate = paymentDate;
	}

	public Location(String locationMacAddress, double permanentContor, 
		       double monthContor,double dayContor, double pasContor, int numberOfMachine,
		String operator, String paymentDate) {

	this.locationMacAddress = locationMacAddress;
	this.permanentContor = permanentContor;
	this.monthContor = monthContor;
	this.dayContor = dayContor;
	this.pasContor = pasContor;
	this.numberOfMachine = numberOfMachine;
	this.operator = operator;
	this.paymentDate = paymentDate;
}

	public Location(int id,  String locationMacAddress, double permanentContor, 
		       double monthContor,double dayContor, double pasContor, int numberOfMachine,
		String operator, String paymentDate) {
	super();
	this.id = id;
	this.locationMacAddress = locationMacAddress;
	this.permanentContor = permanentContor;
	this.monthContor = monthContor;
	this.dayContor = dayContor;
	this.pasContor = pasContor;
	this.numberOfMachine = numberOfMachine;
	this.operator = operator;
	this.paymentDate = paymentDate;
}

	public Location(int id, String locationName, String locationAdress, String locationMacAddress,
			double permanentContor, double monthContor, double dayContor, double pasContor, int numberOfMachine,
			int signalLevel, String paymentDate) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.pasContor = pasContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.paymentDate = paymentDate;
	}



	public Location(String locationName, String locationAdress, String locationMacAddress, double permanentContor,
			double monthContor, double dayContor, double pasContor, int numberOfMachine, int signalLevel,
			String paymentDate) {
		super();
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.pasContor = pasContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.paymentDate = paymentDate;
	}



	public Location(String locationName, String locationMacAddress, double permanentContor, int signalLevel) {
		super();
		this.locationName = locationName;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.signalLevel = signalLevel;
	}


	public Location(String locationName, String locationMacAddress, double permanentContor, double dayContor,
			int signalLevel) {
		super();
		this.locationName = locationName;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.dayContor = dayContor;
		this.signalLevel = signalLevel;
	}



	public Location(String locationName, String locationMacAddress, double permanentContor) {
		super();
		this.locationName = locationName;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
	}


	public Location(int id, String locationName, String locationAdress, String locationMacAddress,
			double permanentContor, double monthContor, double dayContor, int numberOfMachine, int signalLevel,
			String paymentDate) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.paymentDate = paymentDate;
	}



	



	public Location(String locationName, String locationMacAddress, double permanentContor, double dayContor) {
		super();
		this.locationName = locationName;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.dayContor = dayContor;
	}



	


	public Location(int id, String locationName, String locationMacAddress, double permanentContor) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
	}



	


	public Location(String locationName, String locationAdress, String locationMacAddress, double permanentContor,
			double monthContor, double dayContor, int numberOfMachine, int signalLevel) {
		super();
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		
	}









	public Location(int id, String locationName, String locationAdress, String locationMacAddress,
			double permanentContor, double monthContor, double dayContor, int numberOfMachine, int signalLevel,
			String isLogin, String paymentDate, String onlineDate) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.isLogin = isLogin;
		this.paymentDate = paymentDate;
		this.onlineDate = onlineDate;
	}



	public Location(int id, String locationName, String locationAdress, String locationMacAddress,
			double permanentContor, double monthContor, double dayContor, int numberOfMachine, int signalLevel,
			String isLogin, String paymentDate) {
		super();
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.isLogin = isLogin;
		this.paymentDate = paymentDate;
	}



	public Location(int id, String locationName, String locationAdress, String locationMacAddress,
			double permanentContor, double monthContor, double dayContor, int numberOfMachine, int signalLevel) {
		
		this.id = id;
		this.locationName = locationName;
		this.locationAdress = locationAdress;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		
	}



	




	public Location(String locationMacAddress, double permanentContor, double dayContor) {
		super();
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.dayContor = dayContor;
	}



	public Location(String locationMacAddress, double permanentContor, double dayContor, int signalLevel) {
		super();
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.dayContor = dayContor;
		this.signalLevel = signalLevel;
	}



	public Location( String locationMacAddress, double permanentContor, double monthContor, double dayContor,
			double pasContor, int numberOfMachine, int signalLevel, String paymentDate) {
		super();
		
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.pasContor = pasContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.paymentDate = paymentDate;
	}


	public Location(int id, String locationMacAddress, double permanentContor, double monthContor, double dayContor,
			double pasContor, int numberOfMachine, int signalLevel, String paymentDate) {
		super();
		this.id = id;
		this.locationMacAddress = locationMacAddress;
		this.permanentContor = permanentContor;
		this.monthContor = monthContor;
		this.dayContor = dayContor;
		this.pasContor = pasContor;
		this.numberOfMachine = numberOfMachine;
		this.signalLevel = signalLevel;
		this.paymentDate = paymentDate;
	}



	public Location(int id, int idLocation, String month, String year) {
		super();
		this.id = id;
		this.idLocation = idLocation;
		this.month = month;
		this.year = year;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}

	

	public int getIdLocation() {
		return idLocation;
	}



	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}



	public String getLocationName() {
		return locationName;
	}



	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}



	public String getLocationAdress() {
		return locationAdress;
	}



	public void setLocationAdress(String locationAdress) {
		this.locationAdress = locationAdress;
	}



	public String getLocationMacAddress() {
		return locationMacAddress;
	}



	public void setLocationMacAddress(String locationMacAddress) {
		this.locationMacAddress = locationMacAddress;
	}


	public String getMonth() {
		return month;
	}



	public void setMonth(String month) {
		this.month = month;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public double getPermanentContor() {
		return permanentContor;
	}



	public void setPermanentContor(double permanentContor) {
		this.permanentContor = permanentContor;
	}



	public double getMonthContor() {
		return monthContor;
	}



	public void setMonthContor(double monthContor) {
		this.monthContor = monthContor;
	}



	public double getDayContor() {
		return dayContor;
	}



	public double getPasContor() {
		return pasContor;
	}



	public void setPasContor(double pasContor) {
		this.pasContor = pasContor;
	}



	public void setDayContor(double dayContor) {
		this.dayContor = dayContor;
	}



	public int getNumberOfMachine() {
		return numberOfMachine;
	}



	public void setNumberOfMachine(int numberOfMachine) {
		this.numberOfMachine = numberOfMachine;
	}



	public int getSignalLevel() {
		return signalLevel;
	}



	public void setSignalLevel(int signalLevel) {
		this.signalLevel = signalLevel;
	}





	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
	}



	public String getIsLogin() {
		return isLogin;
	}



	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}



	public String getPaymentDate() {
		return paymentDate;
	}



	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}



	public String getOnlineDate() {
		return onlineDate;
	}



	public void setOnlineDate(String onlineDate) {
		this.onlineDate = onlineDate;
	}



}
