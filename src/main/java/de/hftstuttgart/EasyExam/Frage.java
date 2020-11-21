package de.hftstuttgart.EasyExam;

public class Frage {
	
	/*
	 * //* In the simplified Model, a single Class (Corresponding to one singular
	 * DB table) is used to store all the details of a question
	 * 
	 */	
	
	//Model(Frage.Class).Variables(ISDB:=Values) --> New Model.class for question (I=int S=String(s) D=double/decimal B=Bool) 
	//Model(Frage.Class).Var(ISDB,TNBM:=Values) -->T = Themengebiet.class.getThemengebiet, M = MusterLoesung.class.getString....
	
	/*
	 * JAVA LOGIC: is it nessecay/useful to create 3 different objects and only story
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
	private String niveau;
	private String themengebiet;
	private double punkte;
	private boolean gestelltbool;


	private String grundLageNiveau;

	public String getGrundLageNiveau() {
		return grundLageNiveau;
	}

	public void setGrundLageNiveau(String grundLageNiveau) {
		this.grundLageNiveau = grundLageNiveau;
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

	private String gut;
	private String sehrGut;

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		this.ID = id;
	}

	public boolean isGestelltbool() {
		return gestelltbool;
	}

	public void setGestelltbool(boolean gestelltbool) {
		this.gestelltbool = gestelltbool;
	}

	private String musterLoesung;


	public Frage(int id, String themengebiet, String frageStellung, String musterLoesung, String niveau, double punkte,
			Boolean gestellt) {
		this.ID = id;
		this.themengebiet = themengebiet;
		this.frageStellung = frageStellung;
		this.musterLoesung = musterLoesung;
		this.niveau = niveau;
		this.punkte = punkte;
		this.gestelltbool = gestellt;

	}

	public String getFragestellung() {
		return frageStellung;
	}

	public void setFragestellung(String fragestellung) {
		this.frageStellung = fragestellung;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getFrageStellung() {
		return frageStellung;
	}

	public void setFrageStellung(String frageStellung) {
		this.frageStellung = frageStellung;
	}

	public double getPunkte() {
		return punkte;
	}

	public void setPunkte(double punkte) {
		this.punkte = punkte;
	}

	public String getThemengebiet() {
		return themengebiet;
	}

	public void setThemengebiet(String themengebiet) {
		this.themengebiet = themengebiet;
	}



	public String getMusterLoesung() {
		return musterLoesung;
	}

	public void setMusterLoesung(String musterLoesung) {
		this.musterLoesung = musterLoesung;
	}

	public String getMusterloesung() {
		return Musterloesung;
	}

	public void setMusterloesung(String musterloesung) {
		Musterloesung = musterloesung;
	}

}
