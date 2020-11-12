package de.hftstuttgart.EasyExam;

public class Frage {

	private int id;
	private String fragestellung;
	private int niveau;
	private double punkte;
	private Themengebiet themengebiet;
	private boolean gestellt;
	private Musterloesung[] loesungen;
	
	public Frage(int pId, String pFragestellung, int pNiveau, Themengebiet pThemengebiet)
	{
		id = pId;
		fragestellung = pFragestellung;
		niveau = pNiveau;
		themengebiet = pThemengebiet;
	}
	
	public int getId() {
		return id;
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
	public double getPunkte() {
		return punkte;
	}
	public void setPunkte(double punkte) {
		this.punkte = punkte;
	}
	public Themengebiet getThemengebiet() {
		return themengebiet;
	}
	public void setThemengebiet(Themengebiet themengebiet) {
		this.themengebiet = themengebiet;
	}
	public boolean isGestellt() {
		return gestellt;
	}
	public void setGestellt(boolean gestellt) {
		this.gestellt = gestellt;
	}
	public Musterloesung [] getLoesungen() {
		return loesungen;
	}
	public void setLoesungen(Musterloesung[] loesungen) {
		this.loesungen = loesungen;
	}
}
