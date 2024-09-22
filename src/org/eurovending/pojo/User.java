package org.eurovending.pojo;

public class User {
	private int id;
	private String fullName;
	private String phoneNumber;
	private String email;
	private String salt;
	private String password;
	private String retypePassword;
	private String role; //superadmin,admin,employ
	private String statute; //forbidden,active
	private String operator; // nume operator care efectueaza inregistararea
	private String dataEntry;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public User(int id, String fullName, String phoneNumber, String email, String salt, String password,
			String retypePassword, String role, String statute, String operator, String dataEntry) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.salt = salt;
		this.password = password;
		this.retypePassword = retypePassword;
		this.role = role;
		this.statute = statute;
		this.operator = operator;
		this.dataEntry = dataEntry;
	}

	public User(String fullName, String phoneNumber, String email, String salt, String password, String retypePassword,
			String role, String statute, String operator, String dataEntry) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.salt = salt;
		this.password = password;
		this.retypePassword = retypePassword;
		this.role = role;
		this.statute = statute;
		this.operator = operator;
		this.dataEntry = dataEntry;
	}

	public User(String fullName, String phoneNumber, String email, String salt, String password, String role,
			String statute, String operator, String dataEntry) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.salt = salt;
		this.password = password;
		this.role = role;
		this.statute = statute;
		this.operator = operator;
		this.dataEntry = dataEntry;
	}

	public User(int id, String fullName, String phoneNumber, String email, String salt, String password, String role,
			String statute, String operator, String dataEntry) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.salt = salt;
		this.password = password;
		this.role = role;
		this.statute = statute;
		this.operator = operator;
		this.dataEntry = dataEntry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatute() {
		return statute;
	}

	public void setStatute(String statute) {
		this.statute = statute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDataEntry() {
		return dataEntry;
	}

	public void setDataEntry(String dataEntry) {
		this.dataEntry = dataEntry;
	}





}
