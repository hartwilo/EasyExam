package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import DB.DBConn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class ControllerFrageErstellen {

	// Frage
	// erstellen............................................................................................

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
	
	
	

	// DB Related Variables
	public PreparedStatement pst = null;
	
	@FXML
	void frageSpeichern(MouseEvent event) throws SQLException, IOException {

		String stellung = frageStellungTextField.getText();
		String loesung = musterLoesungTextField.getText();
		String niveau = null;

		if (Niveau.getSelectedToggle() != null) {
			String selected = ((RadioButton) Niveau.getSelectedToggle()).getText();
			niveau = selected;
			System.out.print(niveau); // to-be-deleted
		} else {
			System.out.print("Select Niveau"); //// Must eventually be changed to warning output in fx
		}
		;

		String punkte = punktzahl.getText(); // Eingabe muss eingeschränkt werden

		String query = "insert into Fragen(frageStellung, musterLoesung, niveau, punktZahl) Values(?,?,?,?)";
		pst = DBConn.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		pst.setString(1, stellung);
		pst.setString(2, loesung);
		pst.setString(3, niveau);
		pst.setString(4, punkte);
		
		pst.addBatch();
		pst.executeBatch();

		int status = pst.executeUpdate();

		if (status == 1) {
			System.out.print("Frage erfolgreich gespeichert");
			frageStellungTextField.setText("");
			musterLoesungTextField.setText("");
			punktzahl.setText("");
			MainController.setWindow("KatalogErstellen");

		} else {
			System.out.print("Computers hate you!");
		}
	}

}
