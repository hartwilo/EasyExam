package de.hftstuttgart.EasyExam.Models;

public class Pruefung<Katalog> {

	private int idPruefung;
	private String bezeichnung;
	private float note;
	private float punkteGesamt;
	private String fragekatalog;
	private int student_mtkr;
	private int prueferNr;
	
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
	public Pruefung(int idPruefung, String bezeichnung, float note, float punkteGesamt, String fragekatalog, int student_mtkr,
			int prueferNr) {
		super();
		this.idPruefung = idPruefung;
		this.bezeichnung = bezeichnung;
		this.note = note;
		this.punkteGesamt = punkteGesamt;
		this.fragekatalog = fragekatalog;
		this.student_mtkr = student_mtkr;
		this.prueferNr = prueferNr;
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
	
	

	public float getPunkteGesamt() {
		return punkteGesamt;
	}

	public void setPunkteGesamt(float punkteGesamt) {
		this.punkteGesamt = punkteGesamt;
	}

	/**
	 * The method is used to get the catalog
	 * 
	 * @return
	 */
	public String getFragekatalog() {
		return fragekatalog;
	}

	/**
	 * The method is used to set the catalog
	 * 
	 * @param fragekatalog
	 */
	public void setFragekatalog(String fragekatalog) {
		this.fragekatalog = fragekatalog;
	}

	public int getStudent_mtkr() {
		return student_mtkr;
	}

	public void setStudent_mtkr(int student_mtkr) {
		this.student_mtkr = student_mtkr;
	}

	public int getPrueferNr() {
		return prueferNr;
	}

	public void setPrueferNr(int prueferNr) {
		this.prueferNr = prueferNr;
	}
	

	
	
}
