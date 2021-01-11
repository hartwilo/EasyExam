package de.hftstuttgart.EasyExam.Models;

public class Student{

	
	private int matrikelnr;
	private String vorname;
	private String nachname;
	private int semester;
	private String studiengang;
	
	/**
	 * empty constructor 
	 */
	public Student() {
		
	}
	
	/**
	 * Constructor with matrikelnr, vorname, nachname, semester and studiengang
	 * 
	 * @param matrikelnr
	 * @param vorname
	 * @param nachname
	 * @param semester
	 * @param studiengang
	 */
	public Student(int matrikelnr, String vorname, String nachname, int semester, String studiengang) {
		super();
		this.matrikelnr = matrikelnr;
		this.vorname = vorname;
		this.nachname = nachname;
		this.semester = semester;
		this.studiengang = studiengang;
	}

	/**
	 * The method is used to get the Matrikelnr
	 * 
	 * @return
	 */
	public int getMatrikelnr() {
		return matrikelnr;
	}

	/**
	 * The method is used to set the Matrikelnr
	 * 
	 * @param matrikelnr
	 */
	public void setMatrikelnr(int matrikelnr) {
		this.matrikelnr = matrikelnr;
	}

	/**
	 * The method is used to get the name
	 * 
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}

	/**
	 * The method is used to set the name
	 * 
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/**
	 * The method is used to get the surname
	 * 
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}

	/**
	 * The method is used to set the surname
	 * 
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * The method is used to get the semester
	 * 
	 * @return
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * The method is used to set the semester 
	 * 
	 * @param semester
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * The method is used to get the Course of study
	 * 
	 * @return
	 */
	public String getStudiengang() {
		return studiengang;
	}

	/**
	 * The method is used to set the Course of study
	 * 
	 * @param studiengang
	 */
	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	/**
	 * The method is used to print a student to the console 
	 */
	public String toString() {
		
		int m = this.getMatrikelnr(); 
		String v = this.getVorname();
		String n = this.getNachname(); 
		int s = this.getSemester();
		String g = this.getStudiengang(); 
		
		String output = 
		 "MatNr :" + m + System.lineSeparator()
		+"Vorname :"	+v+ System.lineSeparator() 
		+"Nachname :"	+n+ System.lineSeparator() 
		+"Semester :"	+s+ System.lineSeparator()
		+"Studiengang :"	+g;
		
		return output;
		
	}
	
}
