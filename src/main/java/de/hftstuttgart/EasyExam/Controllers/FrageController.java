package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FrageController {
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}
	
	/*
	 * User Input - The following FXML Objects are the only means by which the user
	 * can input data in the current View. The String values of the TextAreas/Fields
	 * and Selected RadioButtons/ComboBox-Items are passed to the constructor for Frage.obj
	 * 
	 */ 
	
	@FXML
	private TextArea frageStellungTextArea;

	@FXML
	private TextArea musterLoesungTextArea;

	@FXML
	private TextField themengebietTextField;

	@FXML
	private TextField punktzahl;

	@FXML
	private ToggleGroup Niveau;
	

	@FXML
	private RadioButton niveauRadioButton1;

	@FXML
	private RadioButton niveauRadioButton2;

	@FXML
	private RadioButton niveauRadioButton3;
	
	//@Author Jana
	@FXML 
	private TextField levelGrundlagenniveau;

	@FXML 
	private TextField levelGut;

	@FXML 
	private TextField levelSehrGut;
	
	@FXML 
	private Label grunlagenniveauLB;

	@FXML 
	private Label gutLB;

	@FXML 
	private Label sehrGutLB;

	@FXML 
	private ComboBox<String> themengebietComboBox;



	// Buttons
	
	@FXML
	private Button frageSpeichern;

	@FXML
	private Button frageLoeschen;

	@FXML
	private Button zueruck;

	@FXML
	private Button frageEditieren;

	// Labels
	
	@FXML
	private Label fragestellungEingebenLB;

	@FXML
	private Label musterloesungEingebenLB;

	@FXML
	private Label puntzahlLB;

	@FXML
	private Label themenGebietEingebenLB;
	
	@FXML
	private Label katalogLabel;
	
	DBQueries dbQuery = new DBQueries();
	public static String themengebiet;
	
	
	
	 
	/*
	 * The following method is used to create a Frage.obj from the input in the View
	 */
	 
	 public Frage createFrageFromView() {
			String themengebiet = themengebietComboBox.getValue();
			if (themengebiet == null) 
				themengebiet = themengebietTextField.getText();
			if (themengebietComboBox.getValue() != null && themengebietTextField.getText() != null) 
				themengebiet = themengebietComboBox.getValue();
			
			int niveau = 0;	
			if ((boolean) niveauRadioButton1.isSelected()) {
				niveau = 1;	
			} else if ((boolean) niveauRadioButton2.isSelected()) {
				niveau = 2;
			} else if ((boolean) niveauRadioButton3.isSelected()) {
				niveau = 3;
			}
			
			int id = 0;
			String stellung = frageStellungTextArea.getText();
			String loesung = musterLoesungTextArea.getText();
			float punkte = Float.parseFloat(punktzahl.getText());
			String fragekatalog = KatalogController.katalogName;
			String modul = "tbd";
			Boolean gestellt = false;
			String grundlage = levelGrundlagenniveau.getText();
			String gut = levelGut.getText();
			String sehrGut = levelSehrGut.getText();
			
			
			return new Frage(id, stellung, loesung, niveau , themengebiet, fragekatalog, punkte, gestellt, modul,grundlage,gut,sehrGut );
					
			
	 }

	/*
	 * The following method is used to save questions into the database - Values
	 * are entered into the GUI's corresponding TextAreas/Fields and/or chosen
	 * from the ComboBox
	 * 
	 */	 
	 public void speichern() throws SQLException, IOException {
		 
		if (frageDetailsKorrektEingegeben()) {
			Frage frage = createFrageFromView();	
			
			int status = 0; 
			try {
				status = dbQuery.frageSpeichern(frage);;
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			if (status == 1) { //If the Update was successful
				infoAnzeigen("Frage erfolgreich gespeichert in Katalog: "+ frage.getFragekatalog());
				
				//Line Seperator ist das gleiche wie /n bei Sys.out.print
				log.info(" "+System.lineSeparator()
						+ "Question succesfuly saved: "+System.lineSeparator()
						+"Fragestellung: "+frage.getFrageStellung() +System.lineSeparator()
						+"Lösung: "+ frage.getMusterloesung() +System.lineSeparator()
						+"Niveau: "+frage.getNiveau() +System.lineSeparator()
						+"Punkte: "+frage.getPunkte() +System.lineSeparator()
						+"Gestellt: "+frage.isGestelltbool() +System.lineSeparator()
						+"Thema: "+frage.getThemengebiet() +System.lineSeparator()
						+"Fragekatalog: "+frage.getFragekatalog() +System.lineSeparator()
						+"Grundlage Niveau: "+frage.getGrundLageNiveau() +System.lineSeparator()
						+"Gut: "+frage.getGut() +System.lineSeparator()
						+"Sehr gut: "+frage.getSehrGut() +System.lineSeparator());
				
				StartController.setWindow("Katalogverwaltung");
			}
			
			} else if (!frageDetailsKorrektEingegeben()) {
				warnungAnzeigen("Die Frage könnte nicht gespeichert werden - Alle Fragedaten bitte richtig eingeben!");
		}
			

	}
	 
//////////////////Java Methods //////////////////////

	/*
	 * Displays a specific warning message.
	 * 
	 * @Author - Bachir
	 */
	private void warnungAnzeigen(String warnung) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText(warnung);
		alert.showAndWait();
	}

	/*
	 * Displays a message to the user; notifying that some event took place
	 * 
	 * @Author - Bachir
	 */ private void infoAnzeigen(String information) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText(information);
		alert.showAndWait();
	}

	/*
	 * The following method is used to make sure the input in the Points TextArea is
	 * restricted to a positive double value => Numeric TextArea
	 * 
	 * @Author - Bachir
	 */ private boolean punkteValidieren() {

		Pattern p = Pattern.compile("^[+]?(([1-9]\\d*))(\\.\\d+)?");
		Matcher m = p.matcher(punktzahl.getText());

		if (m.find() && m.group().equals(punktzahl.getText())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Returns true if the user has entered the data properly and false if otherwise
	 * 
	 * @Author - Bachir
	 * 
	 */
	private boolean frageDetailsKorrektEingegeben() {
		if (punkteValidieren() && !frageStellungTextArea.getText().isEmpty()
				&& !musterLoesungTextArea.getText().isEmpty() && !levelGrundlagenniveau.getText().isEmpty()
				&& !levelGut.getText().isEmpty() && !levelSehrGut.getText().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}
	
	/////////////////// FXML Methods ////////////////////
	
	
	@FXML /*
			 * // The following method is used to fill the Topics ComboBox with all existing
			 * // values in the database.
			 */
	public void themengebieteLaden(MouseEvent event) throws SQLException {
		String fragekatalog = KatalogController.katalogName;
		themengebietComboBox.setItems(dbQuery.themengebieteAuslesen(fragekatalog));

	}
	
	

	@FXML // Save questions through the Speichern Button
	public void frageSpeichern(MouseEvent event) throws SQLException, IOException {
		speichern();
		
	}

	@FXML // Save questions when ENTER key is pressed //Method is currently not used TO-DO:DELETE >?
	void frageSpeichernOnEnter(KeyEvent event) throws SQLException, IOException {

		if (event.getCode().equals(KeyCode.ENTER)) {
			speichern();
		}
	}

	@FXML // GUI - Navigation - Go back to KatalogErstellen screen without creating a new
			// question.
	public void zueruck(MouseEvent event) throws IOException {
		log.info("Adding question cancelled");
		StartController.setWindow("Katalogverwaltung");
	}
	

	@FXML
	void frageEditieren(MouseEvent event) {

	}
	
	@FXML
    void showKatalog(MouseEvent event) {
		katalogLabel.setText("Selektierter Katalog: " + KatalogController.katalogName);
    }
	

}
