package de.hftstuttgart.EasyExam.Models;

public class Pruefung<Katalog> {

	private int idPruefung;
	private String bezeichnung;
	private float note;
	private Katalog fragekatalog;
	private Student student;
	private Pruefer pruefer;
	
	/**
	 * Constructor with ID, bezeichnung, note, fragekatalog, student and pruefer
	 * 
	 * @param idPruefung
	 * @param bezeichnung
	 * @param note
	 * @param fragekatalog
	 * @param student
	 * @param pruefer
	 */
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

	/**
	 * The method is used to get the ID 
	 * 
	 * @return
	 */
	public int getIdPruefung() {
		return idPruefung;
	}

	/**
	 * The method is used to set the ID
	 * 
	 * @param idPruefung
	 */
	public void setIdPruefung(int idPruefung) {
		this.idPruefung = idPruefung;
	}

	/**
	 * The method is used to get the name 
	 * 
	 * @return
	 */
	public String getBezeichnung() {
		return bezeichnung;
	}

	/**
	 * The method is used to set the name
	 * 
	 * @param bezeichnung
	 */
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	/**
	 * The method is used to get the grade 
	 * 
	 * @return
	 */
	public float getNote() {
		return note;
	}

	/**
	 * The method is used to set the grade
	 * 
	 * @param note
	 */
	public void setNote(float note) {
		this.note = note;
	}

	/**
	 * The method is used to get the catalog
	 * 
	 * @return
	 */
	public Katalog getFragekatalog() {
		return fragekatalog;
	}

	/**
	 * The method is used to set the catalog
	 * 
	 * @param fragekatalog
	 */
	public void setFragekatalog(Katalog fragekatalog) {
		this.fragekatalog = fragekatalog;
	}
	
	/**
	 * The method is used to get the student 
	 * 
	 * @return
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * The method is used to set the student 
	 * 
	 * @param student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * The method is used to get the examiner
	 * 
	 * @return
	 */
	public Pruefer getPruefer() {
		return pruefer;
	}

	/**
	 * The method is used to set the examiner
	 * 
	 * @param pruefer
	 */
	public void setPruefer(Pruefer pruefer) {
		this.pruefer = pruefer;
	}
	
	
	
}
