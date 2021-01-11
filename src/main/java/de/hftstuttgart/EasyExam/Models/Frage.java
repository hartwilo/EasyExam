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

	/**
	 * Constructor with id, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt and modul
	 * 
	 * @param iD2
	 * @param frageStellung
	 * @param musterloesung
	 * @param niveau
	 * @param themengebiet
	 * @param fragekatalog
	 * @param punkte
	 * @param gestelltbool
	 * @param modul
	 */
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
	
	/**
	 * Constructor with id, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul and erreichtePunkte
	 * 
	 * @param iD
	 * @param frageStellung
	 * @param musterloesung
	 * @param niveau
	 * @param themengebiet
	 * @param fragekatalog
	 * @param punkte
	 * @param gestelltbool
	 * @param modul
	 * @param erreichtePunkte
	 */
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
	
	/**
	 * Constructor with id, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundlagenniveau, gut, sehrgut and erreichtePunkte 
	 * 
	 * @param iD
	 * @param frageStellung
	 * @param musterloesung
	 * @param niveau
	 * @param themengebiet
	 * @param fragekatalog
	 * @param punkte
	 * @param gestelltbool
	 * @param modul
	 * @param grundLageNiveau
	 * @param gut
	 * @param sehrGut
	 * @param erreichtePunkte
	 */
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
	
	/**
	 * Constructor with id, fragestellung, musterloesung, niveau, themengebiet, fragekatalog, punkte, gestellt, modul, grundlagenniveau, gut ans sehrGut
	 * 
	 * @param iD
	 * @param frageStellung
	 * @param musterloesung
	 * @param niveau
	 * @param themengebiet
	 * @param fragekatalog
	 * @param punkte
	 * @param gestelltbool
	 * @param modul
	 * @param grundLageNiveau
	 * @param gut
	 * @param sehrGut
	 */
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
	
	/**
	 * The method is used to print a question to the console 
	 */
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
 
	/**
	 * The method is used to get the text for this niveau
	 * 
	 * @return
	 */
	public String getGut() {
		return gut;
	}

	/**
	 * The method is used to set the text for this niveau
	 * 
	 * @param gut
	 */
	public void setGut(String gut) {
		this.gut = gut;
	}

	/**
	 * The method is used to get the text for this niveau
	 * 
	 * @return
	 */
	public String getSehrGut() {
		return sehrGut;
	}

	/**
	 * The method is used to set the text for this niveau
	 * 
	 * @param sehrGut
	 */
	public void setSehrGut(String sehrGut) {
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

	/**
	 * empty constructor
	 */
	public Frage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * The method is used to get the id for a question 
	 * 
	 * @return
	 */
	public int getID() {
		return ID;
	}

	/**
	 * The method is used to set an ID
	 * 
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * The method is used to get the question 
	 * 
	 * @return
	 */
	public String getFrageStellung() {
		return frageStellung;
	}

	/**
	 * The method is used to set the question
	 * 
	 * @param frageStellung
	 */
	public void setFrageStellung(String frageStellung) {
		this.frageStellung = frageStellung;
	}

	/**
	 * The method is used to get the solution 
	 * 
	 * @return
	 */
	public String getMusterloesung() {
		return Musterloesung;
	}

	/**
	 * The method is used to set the solution 
	 * 
	 * @param musterloesung
	 */
	public void setMusterloesung(String musterloesung) {
		Musterloesung = musterloesung;
	}

	/**
	 * The method is used to get the level 
	 * 
	 * @return
	 */
	public int getNiveau() {
		return niveau;
	}

	/**
	 * The method is used to set the level
	 * 
	 * @param niveau
	 */
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}

	/**
	 * The method is used to get the topic
	 * 
	 * @return
	 */
	public String getThemengebiet() {
		return themengebiet;
	}

	/**
	 * The method is used to set the topic 
	 * 
	 * @param themengebiet
	 */
	public void setThemengebiet(String themengebiet) {
		this.themengebiet = themengebiet;
	}

	/**
	 * The method is used to get the catalog 
	 * 
	 * @return
	 */
	public String getFragekatalog() {
		return fragekatalog;
	}

	/**
	 * The method is used to set the catalog
	 * 
	 * @param fragekatalog
	 */
	public void setFragekatalog(String fragekatalog) {
		this.fragekatalog = fragekatalog;
	}

	/**
	 * The method is used to get the maximal points
	 * 
	 * @return
	 */
	public double getPunkte() {
		return punkte;
	}

	/**
	 * The method is used to set the maximal points 
	 * 
	 * @param punkte
	 */
	public void setPunkte(float punkte) {
		this.punkte = punkte;
	}

	/**
	 * The method is used to get the gestellt value 
	 * 
	 * @return
	 */
	public boolean isGestelltbool() {
		return gestelltbool;
	}

	/**
	 * The metho is used to set gestellt to true or false 
	 * 
	 * @param gestelltbool
	 */
	public void setGestelltbool(boolean gestelltbool) {
		this.gestelltbool = gestelltbool;
	}

	/**
	 * The method is used to get the module 
	 * 
	 * @return
	 */
	public String getModul() {
		return Modul;
	}

	/**
	 * The method is used to set the module 
	 * 
	 * @param modul
	 */
	public void setModul(String modul) {
		Modul = modul;
	}

	/**
	 * The method is used to get the description of the main level
	 * 
	 * @return
	 */
	public String getGrundLageNiveau() {
		return grundLageNiveau;
	}

	/**
	 * The method is used to describe the main level
	 * 
	 * @param grundLageNiveau
	 */
	public void setGrundLageNiveau(String grundLageNiveau) {
		this.grundLageNiveau = grundLageNiveau;
	}

	/**
	 * The method is used to get the notes 
	 * 
	 * @return
	 */
	public String getNotizien() {
		return Notizien;
	}

	/**
	 * The method is used to set the notes 
	 * 
	 * @param notizien
	 */
	public void setNotizien(String notizien) {
		Notizien = notizien;
	}

	/**
	 * The method is used to get the reached points
	 * 
	 * @return
	 */
	public float getErreichtePunkte() {
		return erreichtePunkte;
	}

	/**
	 * The method is used to set the reached points 
	 * 
	 * @param erreichtePunkte
	 */
	public void setErreichtePunkte(float erreichtePunkte) {
		this.erreichtePunkte = erreichtePunkte;
	}
	
	


	
	

}
