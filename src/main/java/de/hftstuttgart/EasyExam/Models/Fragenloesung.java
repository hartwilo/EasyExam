package de.hftstuttgart.EasyExam.Models;

public class Fragenloesung {
	
	private int idFragenloesung;
	private Frage frage;
	
	/**
	 * Constructor with ID and frage
	 * 
	 * @param idFragenloesung
	 * @param frage
	 */
	public Fragenloesung(int idFragenloesung, Frage frage) {
		super();
		this.idFragenloesung = idFragenloesung;
		this.frage = frage;
	}

	/**
	 * The method is used to get the solution of a question
	 * 
	 * @return
	 */
	public int getIdFragenloesung() {
		return idFragenloesung;
	}

	/**
	 * The method is used to set the solution of a question 
	 * 
	 * @param idFragenloesung
	 */
	public void setIdFragenloesung(int idFragenloesung) {
		this.idFragenloesung = idFragenloesung;
	}

	/**
	 * The method is used to get the question 
	 * 
	 * @return
	 */
	public Frage getFrage() {
		return frage;
	}

	/**
	 * The method is used set the question 
	 * 
	 * @param frage
	 */
	public void setFrage(Frage frage) {
		this.frage = frage;
	}
	
	
	
	


}
