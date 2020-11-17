package de.hftstuttgart.EasyExam;

public class Musterloesung {

	private int idMusterloesung;
	private String loesung; 
	private int niveau;
	
	
	public Musterloesung(int idMusterloesung, String loesung, int niveau) {
		super();
		this.idMusterloesung = idMusterloesung;
		this.loesung = loesung;
		this.niveau = niveau;
	}


	public int getIdMusterloesung() {
		return idMusterloesung;
	}


	public void setIdMusterloesung(int idMusterloesung) {
		this.idMusterloesung = idMusterloesung;
	}


	public String getLoesung() {
		return loesung;
	}


	public void setLoesung(String loesung) {
		this.loesung = loesung;
	}


	public int getNiveau() {
		return niveau;
	}


	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	
	
	
	
}
