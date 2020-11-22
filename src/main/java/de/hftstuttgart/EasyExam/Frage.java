package de.hftstuttgart.EasyExam;

import javafx.scene.control.CheckBox;

public class Frage {

	private int idFrage;
	private String fragestellung;
	private String musterloesung;
	private int niveau;
	private float punkte; 
	private boolean gestellt;
	private String themengebiet;
	private String fragekatalog;
	private String Modul;
	
	
	public Frage(int idFrage, String fragestellung, String musterloesung, int niveau, float punkte, boolean gestellt,
			String themengebiet, String fragekatalog, String modul) {
		super();
		this.idFrage = idFrage;
		this.fragestellung = fragestellung;
		this.musterloesung = musterloesung;
		this.niveau = niveau;
		this.punkte = punkte;
		this.gestellt = gestellt;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		Modul = modul;
	}


	public int getIdFrage() {
		return idFrage;
	}


	public void setIdFrage(int idFrage) {
		this.idFrage = idFrage;
	}


	public String getFragestellung() {
		return fragestellung;
	}


	public void setFragestellung(String fragestellung) {
		this.fragestellung = fragestellung;
	}


	public String getMusterloesung() {
		return musterloesung;
	}


	public void setMusterloesung(String musterloesung) {
		this.musterloesung = musterloesung;
	}


	public int getNiveau() {
		return niveau;
	}


	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}


	public float getPunkte() {
		return punkte;
	}


	public void setPunkte(float punkte) {
		this.punkte = punkte;
	}


	public boolean isGestellt() {
		return gestellt;
	}


	public void setGestellt(boolean gestellt) {
		this.gestellt = gestellt;
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


	public String getModul() {
		return Modul;
	}


	public void setModul(String modul) {
		Modul = modul;
	}
	
	
	
	
	

	
	
}