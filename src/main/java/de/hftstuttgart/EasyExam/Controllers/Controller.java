package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.hftstuttgart.EasyExam.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

	public void setWindow(String FXMLFile) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/GUI/" + FXMLFile + ".fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.currentWindow = FXMLFile;
		Main.mainWindow.centerOnScreen();
		// Main.mainWindow.setMaximized(true);
		Main.mainWindow.setResizable(false);
		Main.mainWindow.show();

	}

	// Startseite..............................................................................
	@FXML
	public AnchorPane startSeite;

	@FXML
	public MenuBar startSeiteMenu;

	@FXML
	public Button katalogErstellen;

	@FXML
	public Button pruefungStarten;

	@FXML
	public Button statistikAnsehen;

	@FXML
	public Button frageSpeichern;

	@FXML
	void katalogErstellen(MouseEvent event) throws IOException {

		setWindow("KatalogErstellen");
	}

	@FXML
	void pruefungStarten(MouseEvent event) throws IOException {

		setWindow("PruefungsDurchfuehrung");

	}

	@FXML
	void statistikAnsehen(MouseEvent event) {

	}

	// Katalog
	// Anlegen...........................................................................................................................

	@FXML
	public Button frageAnlegen;

	@FXML
	public Button katalogSpeichern;

	@FXML
	public Button katalogAnlegen;

	@FXML
	private TextField katalogNameTextField;

	@FXML
	void katalogAnlegen(MouseEvent event) throws IOException {

		setWindow("KatalogErstellen");
	}

	@FXML
	void frageAnlegen(MouseEvent event) throws IOException {

		setWindow("FrageErstellen");
	}

	@FXML
	void katalogSpeichern(MouseEvent event) throws IOException {

		setWindow("AnfangsScreen");
	}

	// Prüfungsdurchführung......................................................................................

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		setWindow("AnfangsScreen");

	}

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
	void frageSpeichern(MouseEvent event) throws SQLException {

		String stellung = frageStellungTextField.getText();
		String loesung = musterLoesungTextField.getText();
		String niveau;

		if (Niveau.getSelectedToggle() != null) {
			String selected = ((RadioButton) Niveau.getSelectedToggle()).getText();
			niveau = selected;
			System.out.print(niveau); // to-be-deleted
		} else {
			System.out.print("Select Niveau"); //// Must eventually be changed to warning output in fx
		}
		;

//	    	String punkte = punktzahl.getText(); //Eingabe muss eingeschränkt werden
//
//	    	pst = DBConn.connection.prepareStatement("insert into Fragen(frageStellung, musterLoesung, niveau, punktZahl) Values(?,?,?,?)");
//	    	pst.setString(1, stellung);
//	    	pst.setString(2, loesung);
//	    	//pst.setString(3, niveau);
//	    	pst.setString(4, punkte);
//
//	    	int status =pst.executeUpdate();
//
//	    	if(status==1) {
//	    		System.out.print("Frage erfolgreich gespeichert");
//	    		frageStellungTextField.setText("");
//	    		musterLoesungTextField.setText("");
//	    		punktzahl.setText("");
//	    	    setWindow("KatalogErstellen");
//	    		
//	    	}
//	    	else {
//	    		System.out.print("Computers hate you!");
//	    	}
	}

}
