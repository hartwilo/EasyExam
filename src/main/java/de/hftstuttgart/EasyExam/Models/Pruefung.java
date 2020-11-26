package de.hftstuttgart.EasyExam.Models;

public class Pruefung<Katalog> {

	private int idPruefung;
	private String bezeichnung;
	private float note;
	private Katalog fragekatalog;
	private Student student;
	private Pruefer pruefer;
	
	
	public Pruefung(int idPruefung, String bezeichnung, float note, Katalog fragekatalog, Student student,
			Pruefer pruefer) {
		super();
		this.idPruefung = idPruefung;
		this.bezeichnung = bezeichnung;
		this.note = note;
		this.fragekatalog = fragekatalog;
		this.student = student;
		this.pruefer = pruefer;
	}


	public int getIdPruefung() {
		return idPruefung;
	}


	public void setIdPruefung(int idPruefung) {
		this.idPruefung = idPruefung;
	}


	public String getBezeichnung() {
		return bezeichnung;
	}


	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}


	public float getNote() {
		return note;
	}


	public void setNote(float note) {
		this.note = note;
	}


	public Katalog getFragekatalog() {
		return fragekatalog;
	}


	public void setFragekatalog(Katalog fragekatalog) {
		this.fragekatalog = fragekatalog;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Pruefer getPruefer() {
		return pruefer;
	}


	public void setPruefer(Pruefer pruefer) {
		this.pruefer = pruefer;
	}
	
	
	
}
