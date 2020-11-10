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
	public Button katalogAnlegen;
	
	@FXML
	public Button frageAnlegen;

	// Start Screen...........................................................................................................................

	@FXML
	void katalogErstellen(MouseEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/KatalogErstellen.fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.mainWindow.setResizable(false); //Retardo Workaround NGL
		Main.mainWindow.show();
	}

	@FXML
	void pruefungStarten(MouseEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/PruefungsDurchfuehrung.fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.mainWindow.setResizable(false); //Retardo Workaround NGL
		Main.mainWindow.show();

	}

	@FXML
	void statistikAnsehen(MouseEvent event) {

	}

	// Katalog Anlegen...........................................................................................................................

	@FXML
	void katalogAnlegen(MouseEvent event) throws IOException {
		

	}

	@FXML
	void frageAnlegen(MouseEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/FrageErstellen.fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.mainWindow.setResizable(false); //Retardo Workaround NGL
		Main.mainWindow.show();
	}
	
	// Prüfungsdurchführung......................................................................................
	

}
