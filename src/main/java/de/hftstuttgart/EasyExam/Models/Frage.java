package de.hftstuttgart.EasyExam.Models;

public class Frage {
	
	/*
	 * //* In the simplified Model, a single Class (Corresponding to one singular
	 * DB table) is used to store all the details of a question
	 * 
	 */	
	
	//Model(Frage.Class).Variables(ISDB:=Values) --> New Model.class for question (I=int S=String(s) D=double/decimal B=Bool) 
	//Model(Frage.Class).Variables(ISDB,TNBM:=Values) -->T = Themengebiet.class.getThemengebiet, M = MusterLoesung.class.getString....
	
	/*
	 * JAVA LOGIC: is it nessecary/useful to create 3 different objects and only story
	 * a single string in them? Why not just instantly Store that string value in a
	 * variable in the Frage.class -> Removes need for extra classes, tables := Extra SQL/Java Logic -> Reduces Complexity
	 * (fewer Queries, fewer ResultSets, fewer while loops)
	 */
	
	
	/*
	 * In the old Model, Themengebiet, Niveau, Musterloesung etc were treated as
	 * seperate Objects, each only storing a String value (Bezeichnung, Modul etc)
	 * and an Int value only used for SQL. This could be simplified if Model,
	 * Themengebiet, Niveau, Musterloesung etc are treated as String Variables of a
	 * Question instead of differenet Classes each only containing a String and Int
	 * Value. (Note: The user can't and shouldn't be able to manipulate the int ID
	 * value as it is something managed by the DBMS)
	 * 
	 */
	
	
	/*
	 * A single instance of a question represents a line/entry in the DB. A set of
	 * such lines represents a Catalog
	 */
	
	

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
	
	
	
	
	public Frage(String frageStellung, String musterloesung, int niveau, String themengebiet,
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
			String fragekatalog, float punkte, boolean gestelltbool, String modul) {
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
			String fragekatalog, float punkte, boolean gestelltbool, String modul, String grundLageNiveau, String gut, String sehrGut) {
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
		this.sehrGut = gut;
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
	
	


	
	

}
