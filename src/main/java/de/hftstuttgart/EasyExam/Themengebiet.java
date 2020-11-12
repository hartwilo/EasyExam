package de.hftstuttgart.EasyExam;

public class Themengebiet {

	private int id;
	private String bezeichnung;
	
	public Themengebiet(int pId, String pBezeichnung)
	{
		id = pId;
		bezeichnung = pBezeichnung;
	}

	public int getId() {
		return id;
	}
	public void setBezeichnung(String pBezeichnung)
	{
		bezeichnung = pBezeichnung;
	}
	public String getBezeichnung()
	{
		return bezeichnung;
	}
}
