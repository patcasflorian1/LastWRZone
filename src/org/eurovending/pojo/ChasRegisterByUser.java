package org.eurovending.pojo;

public class ChasRegisterByUser {

	private int id;
	private  String operator;
	private double previousBalance;
	private double encashment;
	private double payment;
	private double finalBalance;
	private String explanation;
	private String paymentDate;
	
	
	public ChasRegisterByUser() {
		// TODO Auto-generated constructor stub
	}


	public ChasRegisterByUser(int id, String operator, double previousBalance, double encashment, double payment,
			double finalBalance, String explanation, String paymentDate) {
		super();
		this.id = id;
		this.operator = operator;
		this.previousBalance = previousBalance;
		this.encashment = encashment;
		this.payment = payment;
		this.finalBalance = finalBalance;
		this.explanation = explanation;
		this.paymentDate = paymentDate;
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


	public double getPreviousBalance() {
		return previousBalance;
	}


	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}


	public double getEncashment() {
		return encashment;
	}


	public void setEncashment(double encashment) {
		this.encashment = encashment;
	}


	public double getPayment() {
		return payment;
	}


	public void setPayment(double payment) {
		this.payment = payment;
	}


	public double getFinalBalance() {
		return finalBalance;
	}


	public void setFinalBalance(double finalBalance) {
		this.finalBalance = finalBalance;
	}


	public String getExplanation() {
		return explanation;
	}


	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}


	public String getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	
}
