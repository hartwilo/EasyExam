package de.hftstuttgart.EasyExam;

public class Modul {

	private int id;
	private String bezeichnung;
	
	public Modul(int pId, String pBezeichnung)
	{
		id = pId;
		bezeichnung = pBezeichnung;
	}
	
	public int getId() {
		return id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
}
