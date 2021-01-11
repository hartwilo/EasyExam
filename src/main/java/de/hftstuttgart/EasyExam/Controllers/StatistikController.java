package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;



import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class StatistikController extends Application {

    @FXML
    private Label katalogLabel;

    @FXML
    private BarChart<String, Number> notenverteilungTable;

    @FXML
    private CategoryAxis notenLbl;

    @FXML
    private NumberAxis studentenAnzahlLbl;

    
   /* public void start(Stage stage) throws Exception {
        Pane root = (Pane) FXMLLoader.load(getClass().getResource("GUI/Statistik.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
        
    }*/
    
    

	@Override
	public void start(Stage s) throws Exception {
		try {
			XYChart.Series<String, Number> series = new XYChart.Series<>();
		
    	series.setName("Notenverteilung");
    	series.getData().add(new XYChart.Data<>("1,0", 5));
    	series.getData().add(new XYChart.Data<>("1,3", 3));
    	series.getData().add(new XYChart.Data<>("1,7", 2));
    	series.getData().add(new XYChart.Data<>("2,0", 5));
    	series.getData().add(new XYChart.Data<>("2,3", 7));
    	series.getData().add(new XYChart.Data<>("2,7", 8));
    	series.getData().add(new XYChart.Data<>("3,0", 2));
    	series.getData().add(new XYChart.Data<>("3,3", 1));
    	series.getData().add(new XYChart.Data<>("3,7", 3));
    	series.getData().add(new XYChart.Data<>("4,0", 4));    	
    	series.getData().add(new XYChart.Data<>("5,0", 2));    	
    	notenverteilungTable.getData().add(series);  
    	Scene scene = new Scene(notenverteilungTable, 640,480);
    	s.setScene(scene);
    	s.show();
    	System.out.println("erledigt");
    	
	} catch(Exception e) {
		System.out.println("Fehler Exception");
		e.printStackTrace();
		
	}
	}
    
   /* public void showNotenverteilung(Stage primaryStage) {
    	try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StartController.class.getResource("GUI/Statistik.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Notenverteilung");
            dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            StatistikController controller = loader.getController();
            controller.initialize();

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}