package de.hftstuttgart.EasyExam.Main;

import java.io.IOException;

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

	@Override
	public void start(Stage primaryStage) throws IOException {
		mainWindow = primaryStage;
		mainWindow.setTitle("EasyExam");
		showMainView();

	}

	private void showMainView() throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Pruefung2.fxml"));
		
		//TO-DO: Make GUI elements re-sizable
		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		mainWindow.setScene(scene);
		Main.mainWindow.setResizable(false); 
		
		/*
		 * The current View - Windows are always set to a fixed Size -> TO-DO: Make GUI
		 * windows and their elements re-sizable
		 */
		
		
		Main.mainWindow.centerOnScreen();
		mainWindow.show();
		
	}

	public static void main(String[] args) {
		
		DB.DBConn.buildConn();
		launch(args);
	}
}
