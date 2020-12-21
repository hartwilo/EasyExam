package de.hftstuttgart.EasyExam.Models;

public abstract class Person {

	private String nachname;
	private String vorname;
	
	public Person(String pNachname, String pVorname)
	{
		nachname= pNachname;
		vorname = pVorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
}
