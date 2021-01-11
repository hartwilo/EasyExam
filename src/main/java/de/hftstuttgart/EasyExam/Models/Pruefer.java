package de.hftstuttgart.EasyExam.Models;

import java.sql.Connection;
import java.sql.ResultSet;

import com.itextpdf.text.log.SysoCounter;

import DB.DBQueries;

public class Pruefer {

	private int persNr;
	private String nachname;
	private String vorname;
	private String eMail;
	private String passwort;
	
	/**
	 * Constructor with perNr, nachname and vorname
	 * 
	 * @param persNr
	 * @param nachname
	 * @param vorname
	 */
	public Pruefer(int persNr, String nachname, String vorname) {
		super();
		this.persNr = persNr;
		this.nachname = nachname;
		this.vorname = vorname;
	}

	/**
	 * Constructor with perNr, nachname, vorname, email and password 
	 * 
	 * @param persNr
	 * @param nachname
	 * @param vorname
	 * @param eMail
	 * @param passwort
	 */
	public Pruefer(int persNr, String nachname, String vorname, String eMail, String passwort) {
		super();
		this.persNr = persNr;
		this.nachname = nachname;
		this.vorname = vorname;
		this.eMail = eMail;
		this.passwort = passwort;
	}
	
	/**
	 * empty constructor 
	 */
	public Pruefer() {
		
	};

	/**
	 * The method is used to get the personal number
	 * 
	 * @return
	 */
	public int getPersNr() {
		return persNr;
	}

	/**
	 * The method is used to set the personal number
	 * 
	 * @param persNr
	 */
	public void setPersNr(int persNr) {
		this.persNr = persNr;
	}

	/**
	 * The method is used to get the surname 
	 * 
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * The method is used to set the surname
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

	/**
	 * The method is used to get the email
	 * 
	 * @return
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * The method is used to set the email 
	 * 
	 * @param eMail
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * The method is used to get the password 
	 * 
	 * @return
	 */
	public String getPasswort() {
		return passwort;
	}

	/**
	 * The method is used to set the password 
	 * 
	 * @param passwort
	 */
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	
	
	
}
