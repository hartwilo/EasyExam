package de.hftstuttgart.EasyExam.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import de.hftstuttgart.EasyExam.Controllers.PruefungController;
import de.hftstuttgart.EasyExam.Controllers.StartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage mainWindow;
	public static Scene scene;
	public static FXMLLoader loader;
	public static StartController controller;
	public static String currentWindow;
	
	public static PruefungController pCon = new PruefungController();

	/**
	 * The method calls showMainView() and sets the title of the view
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		mainWindow = primaryStage;
		mainWindow.setTitle("EasyExam");
		showMainView();

	}
 
	/**
	 * The method is used to start Login screen
	 * 
	 * @throws IOException
	 */
	private void showMainView() throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Login.fxml"));
//		URL urlCss = new File("src/main/java/css/fragetabelle.css").toURL();
		
		//TO-DO: Make GUI elements re-sizable
		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
//		scene.getStylesheets().add(urlCss.toExternalForm());
		mainWindow.setScene(scene);
		Main.mainWindow.setResizable(true); 
		
		/*
		 * The current View - Windows are always set to a fixed Size -> TO-DO: Make GUI
		 * windows and their elements re-sizable
		 */
		
		
		Main.mainWindow.centerOnScreen();
		mainWindow.show();
		
	}

	/**
	 * The method is used to build the DB connection 
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, IOException {
		
		DB.DBConn.buildConn();
		launch(args);
	}
}
