package de.hftstuttgart.EasyExam;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage mainWindow;
	public static Scene scene;
	public static FXMLLoader loader;
	public static Controller controller;
	public static String currentWindow;

	@Override
	public void start(Stage primaryStage) throws IOException {
		mainWindow = primaryStage;
		mainWindow.setTitle("EasyExam");
		showMainView();

	}

	private void showMainView() throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/AnfangsScreen.fxml"));

		Main.scene = new Scene(root);
		Main.scene.setRoot(root);
		mainWindow.setScene(scene);
		Main.mainWindow.setResizable(false); //Retardo Workaround NGL
		Main.mainWindow.centerOnScreen();
		mainWindow.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
