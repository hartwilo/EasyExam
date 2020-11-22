package de.hftstuttgart.EasyExam.Models;

public class Pruefer extends Person{

	private int personalnr;

	public Pruefer(int pPersonalnr, String pNachname, String pVorname)
	{
		super(pNachname, pVorname);
		personalnr = pPersonalnr;
	}
	
	public int getPersonalnr() {
		return personalnr;
	}
	
}
