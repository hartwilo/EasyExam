package de.hftstuttgart.EasyExam;

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
