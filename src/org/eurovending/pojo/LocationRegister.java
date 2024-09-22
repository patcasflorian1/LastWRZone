package org.eurovending.pojo;

public class LocationRegister {

	private int id;
	private int idLocation;
	private String month;
	private String year;
	
	
	public LocationRegister() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LocationRegister(int idLocation, String month, String year) {
		super();
		this.idLocation = idLocation;
		this.month = month;
		this.year = year;
	}


	public LocationRegister(int id, int idLocation, String month, String year) {
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
	
	
}
