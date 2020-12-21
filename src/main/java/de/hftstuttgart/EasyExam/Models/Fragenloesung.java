package de.hftstuttgart.EasyExam.Models;

public class Fragenloesung {
	
	private int idFragenloesung;
	private Frage frage;
	
	
	public Fragenloesung(int idFragenloesung, Frage frage) {
		super();
		this.idFragenloesung = idFragenloesung;
		this.frage = frage;
	}


	public int getIdFragenloesung() {
		return idFragenloesung;
	}


	public void setIdFragenloesung(int idFragenloesung) {
		this.idFragenloesung = idFragenloesung;
	}


	public Frage getFrage() {
		return frage;
	}


	public void setFrage(Frage frage) {
		this.frage = frage;
	}
	
	
	
	


}
