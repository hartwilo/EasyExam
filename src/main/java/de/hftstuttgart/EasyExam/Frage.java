package de.hftstuttgart.EasyExam;

import javafx.beans.property.*;

public class Frage {

	//private int id;
	private String frageStellung;
	private String niveau;
	private int punkte; //muss evtll auf double geändert werden
	private String themengebiet;
	private boolean gestellt;
	private String musterLoesung;
	
	public Frage(String themengebiet, String frageStellung, String musterLoesung, String niveau, int punkte, Boolean gestellt )
	{
		//id = pId;
		this.themengebiet = themengebiet;
		this.frageStellung = frageStellung;
		this.musterLoesung = musterLoesung;
		this.niveau = niveau;
		this.punkte = punkte;		
		this.gestellt = gestellt;
		
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

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}

	public String getThemengebiet() {
		return themengebiet;
	}

	public void setThemengebiet(String themengebiet) {
		this.themengebiet = themengebiet;
	}

	public boolean isGestellt() {
		return gestellt;
	}

	public void setGestellt(boolean gestellt) {
		this.gestellt = gestellt;
	}

	public String getMusterLoesung() {
		return musterLoesung;
	}

	public void setMusterLoesung(String musterLoesung) {
		this.musterLoesung = musterLoesung;
	}
	
	
}
