package de.hftstuttgart.EasyExam;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;

public class Frage {

	private int ID;
	private String frageStellung;
	private String niveau;
	private double punkte;
	private String themengebiet;
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


	public Frage(int id, String themengebiet, String frageStellung, String musterLoesung, String niveau, double d,
			Boolean gestellt) {
		this.ID = id;
		this.themengebiet = themengebiet;
		this.frageStellung = frageStellung;
		this.musterLoesung = musterLoesung;
		this.niveau = niveau;
		this.punkte = d;
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

}
