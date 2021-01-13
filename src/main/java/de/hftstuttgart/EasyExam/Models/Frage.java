package de.hftstuttgart.EasyExam.Models;

public class Frage {


	private int ID;
	private String frageStellung;
	private String Musterloesung;
	private int niveau;
	private String themengebiet;
	private String fragekatalog;
	private float punkte;
	private boolean gestelltbool;
	private String Modul;
	private String grundLageNiveau;
	private String gut;
	private String sehrGut;
	private String Notizien;
	private float erreichtePunkte;

	
	public Frage(int iD2, String frageStellung, String musterloesung, int niveau, String themengebiet,
			String fragekatalog, float punkte, boolean gestelltbool, String modul) {
		this.frageStellung = frageStellung;
		Musterloesung = musterloesung;
		this.niveau = niveau;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.punkte = punkte;
		this.gestelltbool = gestelltbool;
		Modul = modul;
	}
	
	public Frage(int iD, String frageStellung, String musterloesung, int niveau, String themengebiet,
			String fragekatalog, float punkte, boolean gestelltbool, String modul, Float erreichtePunkte) {
		super();
		ID = iD;
		this.frageStellung = frageStellung;
		Musterloesung = musterloesung;
		this.niveau = niveau;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.punkte = punkte;
		this.gestelltbool = gestelltbool;
		this.Modul = modul;
		this.erreichtePunkte = erreichtePunkte;
	}
	
	
	public String toString() {
		return "Fragestellung: " + this.frageStellung  + System.lineSeparator()
		+ "Lösung: " + this.Musterloesung  + System.lineSeparator()
		+ "Niveau: " + this.niveau  + System.lineSeparator()
		+ "Punkte: " + this.punkte  + System.lineSeparator()
		+ "Gestellt: "   + System.lineSeparator()
		+ "Thema: " + this.themengebiet  + System.lineSeparator() 
		+ "Fragekatalog: " + this.fragekatalog  + System.lineSeparator()
		+ "Grundlage Niveau: " + this.grundLageNiveau  + System.lineSeparator()
		+ "Gut: " +  this.gut  + System.lineSeparator()
		+ "Sehr gut: " + this.sehrGut;  
	}
 
	
	public String getGut() {
		return gut;
	}

	public void setGut(String gut) {
		this.gut = gut;
	}

	public String getSehrGut() {
		return sehrGut;
	}

	public void setSehrGut(String sehrGut) {
		this.sehrGut = sehrGut;
	}

	public Frage(int iD, String frageStellung, String musterloesung, int niveau, String themengebiet,
			String fragekatalog, float punkte, boolean gestelltbool, String modul, String grundLageNiveau, String gut, String sehrGut, Float erreichtePunkte) {
		super();
		ID = iD;
		this.frageStellung = frageStellung;
		Musterloesung = musterloesung;
		this.niveau = niveau;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.punkte = punkte;
		this.gestelltbool = gestelltbool;
		Modul = modul;
		this.grundLageNiveau = grundLageNiveau;
		this.gut = gut;
		this.sehrGut = sehrGut;
		this.erreichtePunkte = erreichtePunkte;
	}
	public Frage(int iD, String frageStellung, String musterloesung, int niveau, String themengebiet,
			String fragekatalog, float punkte, boolean gestelltbool, String modul, String grundLageNiveau, String gut, String sehrGut) {

		ID = iD;
		this.frageStellung = frageStellung;
		Musterloesung = musterloesung;
		this.niveau = niveau;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.punkte = punkte;
		this.gestelltbool = gestelltbool;
		Modul = modul;
		this.grundLageNiveau = grundLageNiveau;
		this.gut = gut;
		this.sehrGut = sehrGut;

	}
	
	
	public Frage(String frageStellung, String musterloesung, int niveau, String themengebiet,
			String fragekatalog, float punkte, boolean gestelltbool, String modul, String grundLageNiveau, String gut, String sehrGut) {

		this.frageStellung = frageStellung;
		Musterloesung = musterloesung;
		this.niveau = niveau;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.punkte = punkte;
		this.gestelltbool = gestelltbool;
		Modul = modul;
		this.grundLageNiveau = grundLageNiveau;
		this.gut = gut;
		this.sehrGut = gut;
	}

	public Frage() {
		// TODO Auto-generated constructor stub
	}

	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getFrageStellung() {
		return frageStellung;
	}


	public void setFrageStellung(String frageStellung) {
		this.frageStellung = frageStellung;
	}


	public String getMusterloesung() {
		return Musterloesung;
	}


	public void setMusterloesung(String musterloesung) {
		Musterloesung = musterloesung;
	}


	public int getNiveau() {
		return niveau;
	}


	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}


	public String getThemengebiet() {
		return themengebiet;
	}


	public void setThemengebiet(String themengebiet) {
		this.themengebiet = themengebiet;
	}


	public String getFragekatalog() {
		return fragekatalog;
	}


	public void setFragekatalog(String fragekatalog) {
		this.fragekatalog = fragekatalog;
	}


	public double getPunkte() {
		return punkte;
	}


	public void setPunkte(float punkte) {
		this.punkte = punkte;
	}


	public boolean isGestelltbool() {
		return gestelltbool;
	}
	
	

	public void setGestelltbool(boolean gestelltbool) {
		this.gestelltbool = gestelltbool;
	}


	public String getModul() {
		return Modul;
	}


	public void setModul(String modul) {
		Modul = modul;
	}


	public String getGrundLageNiveau() {
		return grundLageNiveau;
	}


	public void setGrundLageNiveau(String grundLageNiveau) {
		this.grundLageNiveau = grundLageNiveau;
	}

	public String getNotizien() {
		return Notizien;
	}

	public void setNotizien(String notizien) {
		Notizien = notizien;
	}

	public float getErreichtePunkte() {
		return erreichtePunkte;
	}

	public void setErreichtePunkte(float erreichtePunkte) {
		this.erreichtePunkte = erreichtePunkte;
	}
	
	


	
	

}
