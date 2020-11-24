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
import DB.DBQuerys;
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
	 * and Selected RadioButtons/ComboBox-Items are used to modify the queries which
	 * are later sent to the database for DDL and DML
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
	@FXML //Edits from Jana's Branch
	private TextField levelGrundlagenniveau;

	@FXML //Edits from Jana's Branch
	private TextField levelGut;

	@FXML //Edits from Jana's Branch
	private TextField levelSehrGut;
	
	@FXML //Edits from Jana's Branch
	private Label grunlagenniveauLB;

	@FXML //Edits from Jana's Branch
	private Label gutLB;

	@FXML //Edits from Jana's Branch
	private Label sehrGutLB;

	@FXML //Edits from Jana's Branch
	private ComboBox<String> themengebietComboBox;

	/* End of user input related FXML Objects */

	@FXML
	private Button frageSpeichern;

	@FXML
	private Button frageLoeschen;

	@FXML
	private Button zueruck;

	@FXML
	private Button frageEditieren;

	@FXML
	private Label fragestellungEingebenLB;

	@FXML
	private Label musterloesungEingebenLB;

	@FXML
	private Label puntzahlLB;

	@FXML
	private Label themenGebietEingebenLB;
	
	DBQuerys dbQuery = new DBQuerys();
	
	////////////////// Java Methods //////////////////////
	
	/*
	 * Displays a specific warning message. i.e:
	 * "Frage könnte nicht gespeichert werden - Punkte wurden nicht richtig eingegeben"
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
	 *  Displays a message to the user; notifying that some event took place
	 *  @Author - Bachir
	 */	private void infoAnzeigen(String information) {
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
	 * 
	 */ private boolean punkteValidieren() {
		
		Pattern p = Pattern.compile("^[+]?(([1-9]\\d*))(\\.\\d+)?");
		Matcher m = p.matcher(punktzahl.getText());

		// If the entered values in the FXML Text area are positive numbers
		if (m.find() && m.group().equals(punktzahl.getText())) {
			return true;
			// Make sure the user only enters the correct values
		} else {
			warnungAnzeigen("Frage könnte nicht gespeichert werden - Punkte wurden nicht richtig eingegeben");
			return false;
		}
	}

	/*
	 * Returns true if the user has entered the data properly and false if
	 * otherwise
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


	/*
	 * The following method is used to save questions into the database - Values
	 * are entered into the GUI's corresponding TextAreas/Fields and/or chosen
	 * from the ComboBox
	 * 
	 */
	 

	 public void speichern() throws SQLException, IOException {
		 

		String themengebiet = themengebietComboBox.getValue();
		int selectedNiveau = 0;
		
		if (themengebiet == null) {
			themengebiet = themengebietTextField.getText();
		}

		if (themengebietComboBox.getValue() != null && themengebietTextField.getText() != null) {
			/*
			 * TO-DO: Code for info message: What topic was saved (ComboBox (vs) TextField)
			 */
			themengebiet = themengebietComboBox.getValue();
		}
		
		
		if ((boolean) niveauRadioButton1.isSelected()) {
			selectedNiveau = 1;	
		} else if ((boolean) niveauRadioButton2.isSelected()) {
			selectedNiveau = 2;
		} else if ((boolean) niveauRadioButton3.isSelected()) {
			selectedNiveau = 3;
		}
		
		//Load View Data into variables (V1)
		
		String stellung = frageStellungTextArea.getText();
		String loesung = musterLoesungTextArea.getText();
		String punkte = punktzahl.getText();
		String grundlagenniveau = levelGrundlagenniveau.getText();
		String gut = levelGut.getText();
		String sehrGut = levelSehrGut.getText();
		int niveau = selectedNiveau;
		String gestellt = "0"; // TO-DO: Change!
		String fragekatalog = "test";
		String modul = "tbd";

		 
		if (frageDetailsKorrektEingegeben()) { // Save question into database only if all relevant details are inputed
												// *properly*.
			
			//Add (V1) Variables : Variables for Grading >? //TO-DO: >? Maybe add new Model Class for these? P-V-C
			//@Author Jana
			
			//pst.setString(7, grundlagenniveau);
			//pst.setString(8, gut);
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!    Index out of range error for sehrGut !!!!!!!!!!!!!!!!!!!!! TO-DO
			
			
			
			//pst.setString(9, sehrGut);
			
			//Update the database -> Add the question to the DB
			int status =0;
			try {
				status = dbQuery.frageSpeichern(stellung, loesung, niveau, punkte, gestellt, themengebiet, fragekatalog, modul);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			if (status == 1) { //If the Update was successful
				infoAnzeigen("Frage wurde erfolgreich gespeichert!");
				log.info("Question added to DB Table");
				StartController.setWindow("Katalogverwaltung");
			}
			
			} else if (frageStellungTextArea.getText().isEmpty() || musterLoesungTextArea.getText().isEmpty()) {
				warnungAnzeigen("Die Frage könnte nicht gespeichert werden - Details bitte richtig eingeben!");
		}
			

	}
	
	/////////////////// FXML Methods ////////////////////
	
	
	@FXML /*
			 * // The following method is used to fill the Topics ComboBox with all existing
			 * // values in the database.
			 */
	private ObservableList<String> themengebieteLaden(MouseEvent event) throws SQLException {


		themengebietComboBox.setItems(dbQuery.themengebieteAuslesen());

		return dbQuery.themengebieteAuslesen();
	}

	@FXML // Save questions through the Speichern Button
	public void frageSpeichern(MouseEvent event) throws SQLException, IOException {
		speichern();
		log.info("Frage gespeichert");
		
	}

	@FXML // Save questions when ENTER key is pressed //Method is currently not used TO-DO:DELETE >?
	void frageSpeichernOnEnter(KeyEvent event) throws SQLException, IOException {

		if (event.getCode().equals(KeyCode.ENTER)) {
			speichern();
			log.info("Frage gespeichert");
		}
	}

	@FXML // GUI - Navigation - Go back to KatalogErstellen screen without creating a new
			// question.
	public void zueruck(MouseEvent event) throws IOException {
		StartController.setWindow("Katalogverwaltung");
	}
	

	@FXML
	void frageEditieren(MouseEvent event) {

	}

}
