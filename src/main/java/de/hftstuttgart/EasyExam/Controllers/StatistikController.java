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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
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
	
	boolean tabelleBefuellt = false;

    @FXML
    private Label katalogLabel;

    @FXML
    private BarChart<String, Number> notenverteilungTable;

    @FXML
    private CategoryAxis notenLbl;

    @FXML
    private NumberAxis studentenAnzahlLbl;
    
    @FXML
    private ComboBox<String> katalogWaehlenComboBox;

    @FXML
    private MenuItem PrüfungStarten;

    @FXML
    private MenuItem FragekatalogErstellen;

    @FXML
    private ComboBox<String> sortierenComboBox;

    @FXML
    private Label katalogWaehlenLabel;

    @FXML
    private Label sortierenLabel;

    @FXML
    void FrageKatalogErstellenClick(ActionEvent event) {
    	try {
			StartController.setWindow("Katalogverwaltung");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void PrüfungStartenClick(ActionEvent event) {
    	try {
			StartController.setWindow("Pruefung2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
    
   // public static Stage stage = new Stage();
    

    
    static String katalogName;
    

    @FXML
   void showKataloge(MouseEvent event) throws SQLException {
    	
    	katalogWaehlenComboBox.setItems(db.katalogeAuslesen());
    
    	showData(null);
    	
    	
    	
    }
        
        
    @FXML
    void showData(ActionEvent event) throws SQLException {
    	
	  	katalogWaehlenComboBox.setOnAction(e ->{
	  		
	  		 try {
				initialize();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	});
    }



    
    

	ArrayList<Float> notenList = new ArrayList<>();
	public ArrayList<Float> readGrades() throws SQLException {
	
		katalogName = katalogWaehlenComboBox.getValue();
    	System.out.println("Katalog:" + katalogName);
		ObservableList <Pruefung> pruefungsList = db.allePruefung(katalogName);
		 int counter = pruefungsList.size();
		
		for(int i = 0; i<counter; i++) {
		float note = db.allePruefung(katalogName).get(i).getNote();
		notenList.add(note);
		}
		System.out.println("Noten:" + notenList);
		return notenList;		
	}
	


	

	
	public void initialize() throws SQLException {
		
		
		
		
		
		
    		readGrades();
		
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
			
		for(float note : notenList) {
			
			String s = String.valueOf(note);
			
			switch(s) {
			case "1.0":
				counter1++;
				break;
			case "1.3":
				counter2++;
				break;
			case "1.7":
				counter3++;
				break;
			case "2.0":
				counter4++;
				break;
			case "2.3":
				counter5++;
				break;
			case "2.7":
				counter6++;
				break;
			case "3.0":
				counter7++;
				break;
			case "3.3":
				counter8++;
				break;
			case "3.7":
				counter9++;
				break;
			case "4.0":
				counter10++;
				break;
			case "5.0":
				counter11++;
				break;
			default:
				System.out.println("Keine Noten eingetragen");
				
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
    	if(tabelleBefuellt = false) {
    		notenverteilungTable.getData().add(series);
    		tabelleBefuellt = true;
    	} else {    		
    		notenverteilungTable.getData().clear();
    		notenverteilungTable.getData().add(series);
    		tabelleBefuellt = false;    		
    		System.out.println("counter:" + counter1 + counter2 + counter3);
    	}
    	notenList.clear();
    	  
    	System.out.println("erledigt");

    	
    	
	} catch(Exception e) {
		System.out.println("Fehler Exception");
		e.printStackTrace();
		
	}
	}
}