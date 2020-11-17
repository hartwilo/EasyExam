package de.hftstuttgart.EasyExam;

public class Modul {

	private int modulNr;
	private String bezeichnung;
	
	
	public Modul(int modulNr, String bezeichnung) {
		super();
		this.modulNr = modulNr;
		this.bezeichnung = bezeichnung;
	}


	public int getModulNr() {
		return modulNr;
	}


	public void setModulNr(int modulNr) {
		this.modulNr = modulNr;
	}


	public String getBezeichnung() {
		return bezeichnung;
	}


	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	

	
	
	
}
