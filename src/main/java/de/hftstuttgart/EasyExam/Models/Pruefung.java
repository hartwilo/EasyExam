package de.hftstuttgart.EasyExam.Models;

public class Pruefung<Katalog> {

	private int idPruefung;
	private String bezeichnung;
	private float note;
	private float erreichtePunkte;
	private int fragekatalog;
	private int matrikelnr;
	private int prueferNr;
	
	
	public Pruefung(int idPruefung, String bezeichnung, float note, float erreichtePunkte, int fragekatalog, int matrikelnr,
			int prueferNr) {
		super();
		this.idPruefung = idPruefung;
		this.bezeichnung = bezeichnung;
		this.note = note;
		this.erreichtePunkte = erreichtePunkte;
		this.fragekatalog = fragekatalog;
		this.matrikelnr = matrikelnr;
		this.prueferNr = prueferNr;
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


	public float getErreichtePunkte() {
		return erreichtePunkte;
	}


	public void setErreichtePunkte(float erreichtePunkte) {
		this.erreichtePunkte = erreichtePunkte;
	}


	public int getFragekatalog() {
		return fragekatalog;
	}


	public void setFragekatalog(int fragekatalog) {
		this.fragekatalog = fragekatalog;
	}


	public int getMatrikelnr() {
		return matrikelnr;
	}


	public void setMatrikelnr(int matrikelnr) {
		this.matrikelnr = matrikelnr;
	}


	public int getPrueferNr() {
		return prueferNr;
	}


	public void setPrueferNr(int prueferNr) {
		this.prueferNr = prueferNr;
	}


	
	
	
}
