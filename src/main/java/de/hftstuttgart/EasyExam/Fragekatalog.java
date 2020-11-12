package de.hftstuttgart.EasyExam;

public class Fragekatalog {

	private int id;
	private Frage[] fragen;
	
	public Fragekatalog(int pId)
	{
		id = pId;
	}
	
	public int getId() {
		return id;
	}
	public Frage[] getFragen() {
		return fragen;
	}
	public void setFragen(Frage[] fragen) {
		this.fragen = fragen;
	}
	
	
}
