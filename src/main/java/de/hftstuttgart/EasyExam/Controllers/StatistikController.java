package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Pruefung;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

public class StatistikController {
	
	DBQueries db = new DBQueries(DBConn.connection);
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

    @FXML
    private Label katalogLabel;

    @FXML
    private BarChart<String, Number> notenverteilungTable;

    @FXML
    private CategoryAxis notenLbl;

    @FXML
    private NumberAxis studentenAnzahlLbl;
    
    public static Stage stage = new Stage();

    
	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/Statistik.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Notenverteilung");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();


	}
	

    
	ArrayList<Float> Noten = new ArrayList<>();
	public ArrayList<Float> readGrades() throws SQLException {
	 
		int counter = 1;
		int i = 0;
		while(counter <= 2) {
		
		float note = db.allePruefung().get(i).getNote();
		Noten.add(note);
		i++;
		counter++;
		}
		System.out.println("Noten:" + Noten);
		return Noten;		
	}
	


	

	
	public void initialize() throws SQLException {
		
		
		    int counter1 = 0;
			int counter2 = 0;
			int counter3 = 0;
			int counter4 = 0;
			int counter5 = 0;
			int counter6 = 0;
			int counter7 = 0;
			int counter8 = 0;
			int counter9 = 0;
			int counter10 = 0;
			int counter11 = 0;
		for(float note : Noten) {		
			
			if(note == 1.0) {
				counter1++;	
				System.out.println(counter1);
			} else if(note == 1.3) {
				counter2++;
			} else if(note == 1.7) {
				counter3++;
			} else if(note == 2.0) {
				counter4++;
			} else if(note == 2.3) {
				counter5++;
			} else if(note == 2.7) {
				counter6++;
			} else if(note == 3.0) {
				counter7++;
			} else if(note == 3.3) {
				counter8++;
			} else if(note == 3.7) {
				counter9++;
			} else if(note == 4.0) {
				counter10++;
			} else if(note == 5.0) {
				counter11++;
			} else {
				System.out.println("Keine Note vorhanden");
			}
			}
		
		
		try {
			
			XYChart.Series<String, Number> series = new XYChart.Series<>();
		
    	series.setName("Notenverteilung");
    	series.getData().add(new XYChart.Data<>("1,0", counter1));
    	series.getData().add(new XYChart.Data<>("1,3", counter2));
    	series.getData().add(new XYChart.Data<>("1,7", counter3));
    	series.getData().add(new XYChart.Data<>("2,0", counter4));
    	series.getData().add(new XYChart.Data<>("2,3", counter5));
    	series.getData().add(new XYChart.Data<>("2,7", counter6));
    	series.getData().add(new XYChart.Data<>("3,0", counter7));
    	series.getData().add(new XYChart.Data<>("3,3", counter8));
    	series.getData().add(new XYChart.Data<>("3,7", counter9));
    	series.getData().add(new XYChart.Data<>("4,0", counter10));    	
    	series.getData().add(new XYChart.Data<>("5,0", counter11));    	
    	notenverteilungTable.getData().add(series);  
    	System.out.println("erledigt");
    	readGrades();
    	
    	
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