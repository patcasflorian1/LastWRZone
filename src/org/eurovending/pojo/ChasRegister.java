package org.eurovending.pojo;

public class ChasRegister {

	private int id;
	private  String operator;
	private String month;
	private String year;
	
	
	public ChasRegister() {
		// TODO Auto-generated constructor stub
	}


	public ChasRegister(int id, String operator, String month, String year) {
		super();
		this.id = id;
		this.operator = operator;
		this.month = month;
		this.year = year;
	}

	public ChasRegister( String operator, String month, String year) {
		super();
	
		this.operator = operator;
		this.month = month;
		this.year = year;
	}
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
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
