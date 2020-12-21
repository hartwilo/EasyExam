package de.hftstuttgart.EasyExam.Models;

public class Notes {

	private String notesText;
	private float erreichtePunkte;
	
	public Notes(String notesText, float erreichtePunkte) {
		super();
		this.notesText = notesText;
		this.erreichtePunkte = erreichtePunkte;
	}
	public String getNotesText() {
		return notesText;
	}
	public void setNotesText(String notesText) {
		this.notesText = notesText;
	}
	public float getErreichtePunkte() {
		return erreichtePunkte;
	}
	public void setErreichtePunkte(float erreichtePunkte) {
		this.erreichtePunkte = erreichtePunkte;
	}
	
	
}
