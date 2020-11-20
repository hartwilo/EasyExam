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

	@FXML
	private Button frageLoeschen;

	@FXML
	public TextArea frageStellungTextField;

	@FXML
	public TextArea musterLoesungTextField;

	@FXML
	public RadioButton niveauRadioButton1;

	@FXML
	public ToggleGroup Niveau;

	@FXML
	public Label fragestellungEingebenLB;

	@FXML
	public Label musterloesungEingebenLB;

	@FXML
	public Button themenGebietEingebenBN;

	@FXML
	public TextField punktzahl;

	@FXML
	public Label puntzahlLB;

	@FXML
	public Label themenGebietEingebenLB;

	@FXML
	public RadioButton niveauRadioButton2;

	@FXML
	public RadioButton niveauRadioButton3;

	@FXML
	private TextField themengebietTextField;

	@FXML
	private Label grunlagenniveauLB;

	@FXML
	private Label gutLB;

	@FXML
	private Label sehrGutLB;

	@FXML
	private TextField levelGrundlagenniveau;

	@FXML
	private TextField levelGut;

	@FXML
	private TextField levelSehrGut;

	@FXML
	private ComboBox<String> themengebietComboBox;

	@FXML
	private Button zueruck;

	// DB Related Variables //
	/*
	 * // Initialized prepared Statement which will later be passed executed // with
	 * a query
	 */
	public PreparedStatement pst = null;

	/*
	 * // Initialized query which will later be modified and passed to prepared //
	 * statement
	 */

	public String query = null;

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
		
		String stellung = frageStellungTextField.getText();
		String loesung = musterLoesungTextField.getText();
		String punkte = punktzahl.getText();
		String grundlagenniveau = levelGrundlagenniveau.getText();
		String gut = levelGut.getText();
		String sehrGut = levelSehrGut.getText();
		String niveau = ((RadioButton) Niveau.getSelectedToggle()).getText();
		String gestellt = "0";

		// Save question into database only if all relevant details are inputed.
		if (punkteValidieren() && !frageStellungTextField.getText().isEmpty()
				&& !musterLoesungTextField.getText().isEmpty() && !levelGrundlagenniveau.getText().isEmpty()
				&& !levelGut.getText().isEmpty() && !levelSehrGut.getText().isEmpty()) {

			query = "insert into Fragen(themengebiet, frageStellung, musterLoesung, "
					+ "niveau, punktZahl, gestellt, grundlagenniveau, gut, sehrGut) Values(?,?,?,?,?,?,?,?,?)";
			pst = DBConn.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, themengebiet);
			pst.setString(2, stellung);
			pst.setString(3, loesung);
			pst.setString(4, niveau);
			pst.setString(5, punkte);
			pst.setString(6, gestellt);
			pst.setString(7, grundlagenniveau);
			pst.setString(8, gut);
			pst.setString(9, sehrGut);

			int status = pst.executeUpdate();
			if (status == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Frage wurde gespeichert");
				alert.showAndWait();
			}
			MainController.setWindow("KatalogErstellen");
		} else if (frageStellungTextField.getText().isEmpty() || musterLoesungTextField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText("Bitte vollen Fragedaten eingeben");
			alert.showAndWait();
		}

	}

	/*
	 * // The following method is used to make sure the input in the Points field is
	 * // restricted to a positive double value
	 */
	private boolean punkteValidieren() {
		
		Pattern p = Pattern.compile("^[+]?(([1-9]\\d*)|0)(\\.\\d+)?");
		Matcher m = p.matcher(punktzahl.getText());
		if (m.find() && m.group().equals(punktzahl.getText())) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Punktzahl validieren");
			alert.setHeaderText(null);
			alert.setContentText("Punktzahl bitte richtig eingeben");
			alert.showAndWait();
			return false;
		}

	}

	@FXML /*
			 * // The following method is used to fill the Topics ComboBox with all existing
			 * // values in the database.
			 */
	ObservableList<String> themengebieteLaden(MouseEvent event) throws SQLException {
		
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

	@FXML // Save questions through the Speichern Button
	public void frageSpeichern(MouseEvent event) throws SQLException, IOException {		
		speichern();
	}

	@FXML // Save questions when ENTER key is pressed
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

}
