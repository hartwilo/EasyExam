package de.hftstuttgart.EasyExam;

public class Pruefung {

	private int id;
	private String bezeichnung;
	private Student student;
	private Modul modul;
	private double note;
	private Fragekatalog fragekatalog;
	
	public Pruefung(int pId, String pBezeichnung, Student pStudent, Modul pModul, Fragekatalog pFragekatalog)
	{
		id = pId;
		bezeichnung = pBezeichnung;
		student = pStudent;
		modul = pModul;
		fragekatalog = pFragekatalog;
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
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Modul getModul() {
		return modul;
	}
	public void setModul(Modul modul) {
		this.modul = modul;
	}
	public double getNote() {
		return note;
	}
	public void setNote(double note) {
		this.note = note;
	}
	public Fragekatalog getFragekatalog() {
		return fragekatalog;
	}
	public void setFragekatalog(Fragekatalog fragekatalog) {
		this.fragekatalog = fragekatalog;
	}
	
	public void pruefungStarten()
	{
		
	}
	public void pruefungBeenden()
	{
		
	}
	public void pruefungSpeichern()
	{
		
	}
	public void frageSelektieren()
	{
		
	}
	public void frageAnendern()
	{
		
	}
	
}
