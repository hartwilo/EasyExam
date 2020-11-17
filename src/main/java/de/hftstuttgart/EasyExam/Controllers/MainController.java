package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;

import de.hftstuttgart.EasyExam.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {

	public static void setWindow(String FXMLFile) throws IOException {

		Parent root = FXMLLoader.load(Main.class.getResource("/GUI/" + FXMLFile + ".fxml"));

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

}
