package org.eurovending.pojo;

import java.util.ArrayList;

public class UsersLocationList {
 private int id;
 private String locName;
 private int idUser;
 private int idLocation;
 private int[] idUserList;
 private ArrayList<String> locationName ;
 private int[] idLocationList;
 
	public UsersLocationList() {
		// TODO Auto-generated constructor stub
	}

	
	public UsersLocationList(int id, String locName) {
		super();
		this.id = id;
		this.locName = locName;
	}


	public UsersLocationList( int[] idUserList, int[] idLocationList) {
		super();	
		this.idUserList = idUserList;		
		this.idLocationList = idLocationList;
	}
	
	

	public UsersLocationList(int[] idUserList, ArrayList<String> locationName) {
		super();
		this.idUserList = idUserList;
		this.locationName = locationName;
	}


	
	

	public UsersLocationList(int id, int idUser, int idLocation) {
		super();
		this.id = id;
		this.idUser = idUser;
		this.idLocation = idLocation;
	}
	
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getLocName() {
		return locName;
	}


	public void setLocName(String locName) {
		this.locName = locName;
	}


	public int getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}

	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}



	public int[] getIdUserList() {
		return idUserList;
	}


	public void setIdUserList(int[] idUserList) {
		this.idUserList = idUserList;
	}


	public ArrayList<String> getLocationName() {
		return locationName;
	}


	public void setLocationName(ArrayList<String> locationName) {
		this.locationName = locationName;
	}

	public int[] getIdLocationList() {
		return idLocationList;
	}

	public void setIdLocationList(int[] idLocationList) {
		this.idLocationList = idLocationList;
	}

	


	
	

	
}
