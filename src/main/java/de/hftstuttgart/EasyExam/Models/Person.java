package de.hftstuttgart.EasyExam.Models;

public abstract class Person {

	private String nachname;
	private String vorname;
	
	/**
	 * Constructor with surename and name 
	 * 
	 * @param pNachname
	 * @param pVorname
	 */
	public Person(String pNachname, String pVorname)
	{
		nachname= pNachname;
		vorname = pVorname;
	}
	
	/**
	 * The method is used to get the surename
	 * 
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}
	
	/**
	 * The method is used to set the surename
	 * 
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	/**
	 * The method is used to get the name
	 * 
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}
	
	/**
	 * The method is used to set the name 
	 * 
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
}
