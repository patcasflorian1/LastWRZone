package org.eurovending.utils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import org.eurovending.dao.AccountLocationDAO;
import org.eurovending.dao.LocationCountersListDAO;
import org.eurovending.dao.LocationCountersRegistersDAO;
import org.eurovending.dao.LocationDAO;
import org.eurovending.pojo.Location;
import org.eurovending.pojo.LocationRegister;
public class HallUtils {

	LocationDAO locdao = new LocationDAO();
	Location loc = new Location();
	ArrayList<Location> locList = new ArrayList<Location>();
	ServiceLoger serviceLoger = new ServiceLoger();


	//insert/update location without credit
	public void insertUpdateLoc(Location location) throws SQLException {
		
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String currentMonth = null ; 
		String currentDay = null ;
		serviceLoger.insertLocationListMonthYear();
		if(zonedDateTime.getMonthValue()<10) {
			currentMonth = 0+Integer.toString(zonedDateTime.getMonthValue());
		}else {
			currentMonth = Integer.toString(zonedDateTime.getMonthValue());
		}
		if(zonedDateTime.getDayOfMonth()<10) {
			currentDay = 0+Integer.toString(zonedDateTime.getDayOfMonth());
		}else {
			currentDay = Integer.toString(zonedDateTime.getDayOfMonth());
		}
		
		locList = locdao.getAllLocation();		
		int findLoc = 0;
		for(Location l : locList) {
			if(location.getLocationMacAddress().equalsIgnoreCase(l.getLocationMacAddress())) {
				findLoc ++;
			}
		}
		if(findLoc == 0) {
		locdao.insertLocation(location);
		location = locdao.getLocationByMacAddr(location.getLocationMacAddress());
		//lRegDao.createTableRegister(location.getId());		
		//servLocReg.createLocationMhontly(location.getId());
		}
		else {
			loc =locdao.getLocationByMacAddr(location.getLocationMacAddress());	
			String strDate = loc.getPaymentDate();			
	        String[] arrOfStr = strDate.split(" ");		
	      strDate = arrOfStr[0];
	      arrOfStr = strDate.split("-");
	     
	      String day = arrOfStr[2];
	      String month = arrOfStr[1];
	      loc.setSignalLevel(location.getSignalLevel());
	      
	      if(day.equalsIgnoreCase(currentDay)) {
				loc.setDayContor((location.getDayContor()*loc.getPasContor())+loc.getDayContor());
			}else {
				loc.setDayContor(location.getDayContor()*loc.getPasContor());
				
			}
			
			if(month.equalsIgnoreCase(currentMonth)) {
				loc.setMonthContor(loc.getMonthContor()+(location.getDayContor()*loc.getPasContor()));
			}else {
				loc.setMonthContor(location.getDayContor()*loc.getPasContor());
			}
			
			for(Location l : locList) {
				String strDateL = l.getPaymentDate();				
		        String[] arrOfStrL = strDateL.split(" ");		
		      strDateL = arrOfStrL[0];
		      arrOfStrL = strDateL.split("-");		     
		      String dayL = arrOfStrL[2];
		      String monthL = arrOfStrL[1];
		      System.out.println("DayCont "+l.getDayContor());
		      if(!dayL.equalsIgnoreCase(currentDay)) {
		      l.setDayContor(0);
		      locdao.updateLocationTime(l);
			           }
			   }
			}
			locdao.updateLocationTime(loc);
		}
	
	
	//update location with credit
	public void updateContorHall(Location location) throws SQLException {
		LocationCountersRegistersDAO locCountRegDao = new LocationCountersRegistersDAO();
		LocationCountersListDAO locCountDao = new LocationCountersListDAO();
		AccountLocationDAO accountLocDao = new AccountLocationDAO();
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime zonedDateTime = now.atZone(ZoneId.systemDefault());
		String currentMonth = null ; 
		String currentDay = null ;
		String year = Integer.toString(zonedDateTime.getYear()) ;
		serviceLoger.insertLocationListMonthYear();	
		
		if(zonedDateTime.getMonthValue()<10) {
			currentMonth = 0+Integer.toString(zonedDateTime.getMonthValue());
		}else {
			currentMonth = Integer.toString(zonedDateTime.getMonthValue());
		}
		if(zonedDateTime.getDayOfMonth()<10) {
			currentDay = 0+Integer.toString(zonedDateTime.getDayOfMonth());
		}else {
			currentDay = Integer.toString(zonedDateTime.getDayOfMonth());
		}
		loc =locdao.getLocationByMacAddr(location.getLocationMacAddress());
		accountLocDao.createTableAccountLocation(loc.getId());
		int accountCount = accountLocDao.verifyTableAccountLocation(loc.getId());
		locCountRegDao.createTableLocationCountersRegisters(loc.getId());
		locCountDao.createTableLocationCountersList(loc.getId(), currentMonth, year);
		// loc.setSignalLevel(location.getSignalLevel());
		loc.setPermanentContor(location.getPermanentContor());
		Location accountLoc = new Location(loc.getLocationMacAddress(),loc.getPermanentContor(),0,0,loc.getPasContor(),loc.getNumberOfMachine(),"",loc.getPaymentDate());
		if(accountCount == 0) {
			
			accountLocDao.insertAccountLocation(loc, loc.getId());
		}
		if(loc.getPasContor() == 0) {
		loc.setPasContor(1);
		}
		String strDate = loc.getPaymentDate();
        String[] arrOfStr = strDate.split(" ");		
      strDate = arrOfStr[0];
      arrOfStr = strDate.split("-");
      String day = arrOfStr[2];
      String month = arrOfStr[1];
		if(day.equalsIgnoreCase(currentDay)) {
			
			loc.setDayContor((location.getDayContor()*loc.getPasContor())+loc.getDayContor());
		}else {
			loc.setDayContor(location.getDayContor()*loc.getPasContor());
		}
		
		if(month.equalsIgnoreCase(currentMonth)) {
			loc.setMonthContor(loc.getMonthContor()+(location.getDayContor()*loc.getPasContor()));
		}else {
			loc.setMonthContor(location.getDayContor()*loc.getPasContor());
		}
		int count = locCountRegDao.verifyTableLocationCountersRegistersContainMonthYear(loc.getId(),  currentMonth, year);
		if (count == 0) {
			locCountRegDao.insertLocationCountersRegisters(loc.getId(),  currentMonth, year);
		}
		accountLoc = accountLocDao.getLastAccountLocation(loc.getId());
		accountLoc.setDayContor(accountLoc.getDayContor()+(location.getDayContor()*accountLoc.getPasContor()));
		accountLoc.setMonthContor(accountLoc.getMonthContor()+(location.getDayContor()*accountLoc.getPasContor()));
		accountLocDao.updateAccountLocation(accountLoc, loc.getId());
		locCountDao.insertLocationCountersList(loc, currentMonth, year);
		locdao.updateLocationContor(loc);
		
	}
	
	public double totalDayContor(ArrayList<Location> locationList) {
		double totalDayCount = 0;
		for(Location l:locationList) {
			totalDayCount = totalDayCount + l.getDayContor();
		}
		
		return totalDayCount;
		
	}
	public double totalMonthContor(ArrayList<Location> locationList) {
		double totalMonthCount = 0;
		for(Location l:locationList) {
			totalMonthCount = totalMonthCount + l.getMonthContor();
		}
		
		return totalMonthCount;
		
	}
	
	public int totalMachine(ArrayList<Location> locationList) {
		int totalMachine = 0;
		for(Location l:locationList) {
			if(l.getIsLogin().equalsIgnoreCase("OnLine")) {
			totalMachine = totalMachine + l.getNumberOfMachine();
			}
		}
		
		return totalMachine;
		
	}
	
	}

