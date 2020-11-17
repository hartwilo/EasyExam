package de.hftstuttgart.EasyExam;

import javafx.beans.property.*;

public class Frage {

	private int idFrage;
	private String fragestellung;
	private int niveau;
	private float punkte; 
	private boolean gestellt;
	private String themengebiet;
	private int fragekatalog;
	private Musterloesung musterloesung;
	
	
	public Frage(int idFrage, String fragestellung, int niveau, float punkte, boolean gestellt,
			String themengebiet, int fragekatalog) {
		super();
		this.idFrage = idFrage;
		this.fragestellung = fragestellung;
		this.niveau = niveau;
		this.punkte = punkte;
		this.gestellt = gestellt;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
	}
	
	


	public Frage(int idFrage, String fragestellung, int niveau, float punkte, boolean gestellt, String themengebiet,
			int fragekatalog, Musterloesung musterloesung) {
		super();
		this.idFrage = idFrage;
		this.fragestellung = fragestellung;
		this.niveau = niveau;
		this.punkte = punkte;
		this.gestellt = gestellt;
		this.themengebiet = themengebiet;
		this.fragekatalog = fragekatalog;
		this.musterloesung = musterloesung;
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


	public int getFragekatalog() {
		return fragekatalog;
	}


	public void setFragekatalog(int fragekatalog) {
		this.fragekatalog = fragekatalog;
	}




	public Musterloesung getMusterloesung() {
		return musterloesung;
	}




	public void setMusterloesung(Musterloesung musterloesung) {
		this.musterloesung = musterloesung;
	}
	
	
	
	



}
