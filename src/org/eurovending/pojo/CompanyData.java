package org.eurovending.pojo;

public class CompanyData {

	private int id;
	private String denumire; // denumire societate
	private String nrONRC; // numar inregistrare onrc
	private String cui; // cui firma
	private String adress; // adresa firma 
	private String dataEntry;
	
	
	
	public CompanyData() {
		// TODO Auto-generated constructor stub
	}



	public CompanyData(String denumire, String nrONRC, String cui, String adress, String dataEntry) {
		super();
		this.denumire = denumire;
		this.nrONRC = nrONRC;
		this.cui = cui;
		this.adress = adress;
		this.dataEntry = dataEntry;
	}



	public CompanyData(int id, String denumire, String nrONRC, String cui, String adress, String dataEntry) {
		super();
		this.id = id;
		this.denumire = denumire;
		this.nrONRC = nrONRC;
		this.cui = cui;
		this.adress = adress;
		this.dataEntry = dataEntry;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDenumire() {
		return denumire;
	}



	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}



	public String getNrONRC() {
		return nrONRC;
	}



	public void setNrONRC(String nrONRC) {
		this.nrONRC = nrONRC;
	}



	public String getCui() {
		return cui;
	}



	public void setCui(String cui) {
		this.cui = cui;
	}



	public String getAdress() {
		return adress;
	}



	public void setAdress(String adress) {
		this.adress = adress;
	}



	public String getDataEntry() {
		return dataEntry;
	}



	public void setDataEntry(String dataEntry) {
		this.dataEntry = dataEntry;
	}

	
}
