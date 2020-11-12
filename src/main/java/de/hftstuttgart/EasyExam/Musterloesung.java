package de.hftstuttgart.EasyExam;

public class Musterloesung {

	private int id;
	private String loesung; 
	private int niveau;
	
	public Musterloesung(int pId, String pLoesung, int pNiveau)
	{
		id = pId;
		loesung = pLoesung; 
		niveau = pNiveau;
	}
	
	public int getId() {
		return id;
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
