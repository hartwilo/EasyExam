package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DB.DBConn;
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

public class ControllerFrageErstellen {
	
	
	/*
	 * //User Input - The following FXML Objects are the only means by which the
	 * user can input data in the current View. The String values of the
	 * TextAreas/Fields and Selected RadioButtons/ComboBox-Items are used to modify
	 * the queries which are later sent to the database for DDL and DML
	 *
	 */ @FXML
	public TextArea frageStellungTextArea;

	@FXML
	public TextArea musterLoesungTextArea;

	@FXML
	private TextField themengebietTextField;

	@FXML
	public TextField punktzahl;

	@FXML
	public ToggleGroup Niveau;

	@FXML
	public RadioButton niveauRadioButton1;

	@FXML
	public RadioButton niveauRadioButton2;

	@FXML
	public RadioButton niveauRadioButton3;

	@FXML
	private TextField levelGrundlagenniveau;

	@FXML
	private TextField levelGut;

	@FXML
	private TextField levelSehrGut;

	@FXML
	private ComboBox<String> themengebietComboBox;

	/* End of user input FXML Objects */

	@FXML
	private Button frageSpeichern;

	@FXML
	private Button frageLoeschen;

	@FXML
	private Button zueruck;

	@FXML
	private Button frageEditieren;

	@FXML
	public Label fragestellungEingebenLB;

	@FXML
	public Label musterloesungEingebenLB;

	@FXML
	public Label puntzahlLB;

	@FXML
	public Label themenGebietEingebenLB;
	@FXML
	private Label grunlagenniveauLB;

	@FXML
	private Label gutLB;

	@FXML
	private Label sehrGutLB;
	


		// DB Related Variables //
	/*
	 * // Initialized prepared Statement which will later beexecuted // with
	 * a query 
	 */	public PreparedStatement pst = null;


	/*
	 * // Initialized query which will later be modified and passed to prepared //
	 * statement
	 */ public static String query = null;
	
	 
	 public ResultSet sendQuery(String query) throws SQLException {
		 	
		 	FrageController.query = query;
			pst = DBConn.connection.prepareStatement(FrageController.query);
			ResultSet rs = pst.executeQuery(FrageController.query);
			
			return rs;
	 }
	 
	
	//TO-DO: Could come in handy later - needs to be implemented properly though
	public static String prepQuery(String what, String table, String attribute, String value) {
		return  "SELECT " +what+ "FROM " +table+ "WHERE " +attribute+ " = " +value;	 
	}
	

	/*
	 * // The following method is used to make sure the input in the Points TextArea is
	 * // restricted to a positive double value => Numeric TextArea
	 */
	private boolean punkteValidieren() {

		Pattern p = Pattern.compile("^[+]?(([1-9]\\d*)|0)(\\.\\d+)?");
		Matcher m = p.matcher(punktzahl.getText());

		// If the entered values in the FXML Text area are positive numbers
		if (m.find() && m.group().equals(punktzahl.getText())) {
			return true;
			// Make sure the user only enters the correct values
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Frage könnte nicht gespeichert werden - Punkte wurden nicht richtig eingegeben");
			alert.showAndWait();
			return false;
		}
	}

	/*
	 * // Returns true if the user has entered the data properly and false if
	 * otherwise
	 */	private boolean frageDetailsKorrektEingegeben() {
		if (punkteValidieren() && !frageStellungTextArea.getText().isEmpty()
				&& !musterLoesungTextArea.getText().isEmpty() && !levelGrundlagenniveau.getText().isEmpty()
				&& !levelGut.getText().isEmpty() && !levelSehrGut.getText().isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	@FXML /*
			 * // The following method is used to fill the Topics ComboBox with all existing
			 * // values in the database.
			 */
	private ObservableList<String> themengebieteLaden(MouseEvent event) throws SQLException {

		ObservableList<String> themengebiete = FXCollections.observableArrayList();
		query = "Select themengebiet from Fragen";
		pst = DBConn.connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery(query);

		while (rs.next()) {
			String s = rs.getString("themengebiet");
			if (!themengebiete.contains(s)) {
				themengebiete.add(s);
			}
		}

		themengebietComboBox.setItems(themengebiete);
		
		return themengebiete;
	}

	/*
	 * // The following method is used to save questions into the database - Values
	 * are // entered into the GUI's corresponding TextAreas/Fields and/or // chosen
	 * from the ComboBox
	 */
	public void speichern() throws SQLException, IOException {

		String themengebiet = themengebietComboBox.getValue();

		if (themengebiet == null) {
			themengebiet = themengebietTextField.getText();
		}

		if (themengebietComboBox.getValue() != null && themengebietTextField.getText() != null) {
			/*
			 * // Evtll muss man einen Warning Box erstellen - Hinweis auf welche wert //
			 * übernommen wird! Oder einen option ausblenden wenn die andere benutzt wird
			 */
			themengebiet = themengebietComboBox.getValue();
		}
		
		//Load View Data into variables (V1)
		String stellung = frageStellungTextArea.getText();
		String loesung = musterLoesungTextArea.getText();
		String punkte = punktzahl.getText();
		String grundlagenniveau = levelGrundlagenniveau.getText();
		String gut = levelGut.getText();
		String sehrGut = levelSehrGut.getText();
		String niveau = ((RadioButton) Niveau.getSelectedToggle()).getText();
		String gestellt = "0"; // TO-DO: Change!

		 
		if (frageDetailsKorrektEingegeben()) { // Save question into database only if all relevant details are inputed
												// *properly*.

			query = "insert into Fragen(themengebiet, frageStellung, musterLoesung, "
					+ "niveau, punktZahl, gestellt, grundlagenniveau, gut, sehrGut) Values(?,?,?,?,?,?,?,?,?)";
			pst = DBConn.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			//Add (V1) variables 
			pst.setString(1, themengebiet);
			pst.setString(2, stellung);
			pst.setString(3, loesung);
			pst.setString(4, niveau);
			pst.setString(5, punkte);
			pst.setString(6, gestellt);
			
			//Add (V1) Variables : Variables for Grading >? //TO-DO: >? Maybe add new Model Class for these? P-V-C
			pst.setString(7, grundlagenniveau);
			pst.setString(8, gut);
			pst.setString(9, sehrGut);
			
			//Update the database - Add the question
			int status = pst.executeUpdate();
			
			if (status == 1) { //If the Update was successful
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Frage wurde gespeichert");
				alert.showAndWait();
				MainController.setWindow("KatalogErstellen");
			}
			} else if (frageStellungTextArea.getText().isEmpty() || musterLoesungTextArea.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Die Frage könnte nicht gespiechert werden - Details bitte richtig eingeben!");
				alert.showAndWait();
		}

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
		MainController.setWindow("KatalogErstellen");
	}
	

	@FXML
	void frageEditieren(MouseEvent event) {

	}

}
