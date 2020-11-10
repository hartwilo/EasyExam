package de.hftstuttgart.EasyExam;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {
	
	public void setWindow(String FXMLFile) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/" + FXMLFile + ".fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.currentWindow = FXMLFile;
		Main.mainWindow.centerOnScreen();
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
	private Button zueruckDurchfuehrung;

	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		setWindow("AnfangsScreen");

	}

	// Frage
	// erstellen............................................................................................
	@FXML
	void frageSpeichern(MouseEvent event) throws IOException {

		setWindow("KatalogErstellen");

	}

}
