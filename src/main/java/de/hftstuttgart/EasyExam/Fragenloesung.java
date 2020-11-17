package de.hftstuttgart.EasyExam;

public class Fragenloesung {
	
	private int idFragenloesung;
	private Musterloesung musterloesung;
	private Frage frage;
	
	
	public Fragenloesung(int idFragenloesung, Musterloesung musterloesung, Frage frage) {
		super();
		this.idFragenloesung = idFragenloesung;
		this.musterloesung = musterloesung;
		this.frage = frage;
	}


	public int getIdFragenloesung() {
		return idFragenloesung;
	}


	public void setIdFragenloesung(int idFragenloesung) {
		this.idFragenloesung = idFragenloesung;
	}


	public Musterloesung getMusterloesung() {
		return musterloesung;
	}


	public void setMusterloesung(Musterloesung musterloesung) {
		this.musterloesung = musterloesung;
	}


	public Frage getFrage() {
		return frage;
	}


	public void setFrage(Frage frage) {
		this.frage = frage;
	}
	
	

}
