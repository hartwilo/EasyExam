package de.hftstuttgart.EasyExam.Models;

public class Pruefer {

	private int persNr;
	private int nachname;
	private int vorname;
	
	
	public Pruefer(int persNr, int nachname, int vorname) {
		super();
		this.persNr = persNr;
		this.nachname = nachname;
		this.vorname = vorname;
	}


	public int getPersNr() {
		return persNr;
	}


	public void setPersNr(int persNr) {
		this.persNr = persNr;
	}


	public int getNachname() {
		return nachname;
	}


	public void setNachname(int nachname) {
		this.nachname = nachname;
	}


	public int getVorname() {
		return vorname;
	}


	public void setVorname(int vorname) {
		this.vorname = vorname;
	}

	
	
	
}
