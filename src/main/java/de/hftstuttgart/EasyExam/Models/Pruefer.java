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
	
	
	public Pruefer(int persNr, String nachname, String vorname) {
		super();
		this.persNr = persNr;
		this.nachname = nachname;
		this.vorname = vorname;
	}


	public Pruefer(int persNr, String nachname, String vorname, String eMail, String passwort) {
		super();
		this.persNr = persNr;
		this.nachname = nachname;
		this.vorname = vorname;
		this.eMail = eMail;
		this.passwort = passwort;
	}
	
	public Pruefer() {
		
	};


	public int getPersNr() {
		return persNr;
	}


	public void setPersNr(int persNr) {
		this.persNr = persNr;
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


	public String geteMail() {
		return eMail;
	}


	public void seteMail(String eMail) {
		this.eMail = eMail;
	}


	public String getPasswort() {
		return passwort;
	}


	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	
	
	
}
