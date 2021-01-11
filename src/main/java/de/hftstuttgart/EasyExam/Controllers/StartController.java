package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.util.logging.Logger;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Main.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StartController { 
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	public static void setWindow(String FXMLFile) throws IOException {

		Parent root = FXMLLoader.load(Main.class.getResource("/GUI/" + FXMLFile + ".fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		Main.mainWindow.setScene(Main.scene);
		Main.currentWindow = FXMLFile;
		Main.mainWindow.centerOnScreen();
		Main.mainWindow.setResizable(false);
		Main.mainWindow.show();

	}

	
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


	
	

	@FXML //GUI Navigation - Go to KatalogErstellen screen
	void katalogErstellen(MouseEvent event) throws IOException {

		setWindow("Katalogverwaltung2");
	}

	@FXML //GUI Navigation - Go to PruefungsDurchfuehrung screen
	void pruefungStarten(MouseEvent event) throws IOException {
		
		setWindow("Pruefung2");

	}

	@FXML
	void statistikAnsehen(MouseEvent event) {
		System.out.println("Good luck son!");
	}
	
	

}
