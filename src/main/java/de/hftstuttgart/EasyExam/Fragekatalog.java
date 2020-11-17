package de.hftstuttgart.EasyExam;

public class Fragekatalog {

	private int idFragekatalog;
	private Modul modul;
	
	
	
	
	public Fragekatalog(int idFragekatalog, Modul modul) {
		super();
		this.idFragekatalog = idFragekatalog;
		this.modul = modul;
	}




	public int getIdFragekatalog() {
		return idFragekatalog;
	}




	public void setIdFragekatalog(int idFragekatalog) {
		this.idFragekatalog = idFragekatalog;
	}




	public Modul getModul() {
		return modul;
	}




	public void setModul(Modul modul) {
		this.modul = modul;
	}
	
	
	

	
}
