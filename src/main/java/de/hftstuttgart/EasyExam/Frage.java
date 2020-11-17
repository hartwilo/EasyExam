package de.hftstuttgart.EasyExam;

import javafx.scene.control.CheckBox;

public class Frage {

	// private int id;
	private String frageStellung;
	private String niveau;
	private double punkte; // muss evtll auf double geändert werden
	private String themengebiet;
	private boolean gestelltbool;
	
	public boolean isGestelltbool() {
		return gestelltbool;
	}

	public void setGestelltbool(boolean gestelltbool) {
		this.gestelltbool = gestelltbool;
	}

	public CheckBox getGestellt() {
		return gestellt;
	}

	public void setGestellt(CheckBox gestellt) {
		this.gestellt = gestellt;
	}

	private String musterLoesung;
	private CheckBox gestellt;

	public Frage(String themengebiet, String frageStellung, String musterLoesung, String niveau, double d,
			Boolean gestellt) {
		// id = pId;
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

//	public boolean isGestellt() {
//		return gestellt;
//	}
//
//	public void setGestellt(boolean gestellt) {
//		this.gestellt = gestellt;
//	}

	public String getMusterLoesung() {
		return musterLoesung;
	}

	public void setMusterLoesung(String musterLoesung) {
		this.musterLoesung = musterLoesung;
	}

}
