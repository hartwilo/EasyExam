package de.hftstuttgart.EasyExam.Models;

public class Student extends Person{

	private int matrikelnr;
	private String studiengang;
	private int semester;
	
	public Student(int pMatrikelnr, String pNachname, String pVorname)
	{
		super(pNachname, pVorname);
		matrikelnr = pMatrikelnr;
	}
	
	public int getMatrikelnr() {
		return matrikelnr;
	}
	public String getStudiengang() {
		return studiengang;
	}
	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	
	
}
