package de.hftstuttgart.EasyExam.Models;

public class Student{

	
	private int matrikelnr;
	private String vorname;
	private String nachname;
	private int semester;
	private String studiengang;
	
	public Student() {
		
	}
	
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

	
	public String toString() {
		
		int m = this.getMatrikelnr(); 
		String v = this.getVorname();
		String n = this.getNachname(); 
		int s = this.getSemester();
		String g = this.getStudiengang(); 
		
		String output = m + " " +v+ " " + n+ " " +s+ " " + g;
		
		return output;
		
	}
	
}
