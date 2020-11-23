package de.hftstuttgart.EasyExam.Models;

public class Student{

	
	private int matrikelnr;
	private String vorname;
	private String nachname;
	private int semester;
	private String studiengang;
	
	public Student(int matrikelnr, String vorname, String nachname, int semester, String studiengang) {
		super();
		this.matrikelnr = matrikelnr;
		this.vorname = vorname;
		this.nachname = nachname;
		this.semester = semester;
		this.studiengang = studiengang;
	}

	public int getMatrikelnr() {
		return matrikelnr;
	}

	public void setMatrikelnr(int matrikelnr) {
		this.matrikelnr = matrikelnr;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	

	
}
