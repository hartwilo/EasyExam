package de.hftstuttgart.EasyExam.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.Note;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;


public class NotizienController implements Initializable {
	private final static Logger log = Logger.getLogger(NotizienController.class.getName());
	
	
	//DBqueries Instance
	private Connection db_connection = DBConn.connection;
	private DBQueries dbQueries = new DBQueries(db_connection);
	
	//Question that will be updated. Receives it's value from the selected item in the Pruefung TableView
	Frage frage = new Frage();
	
	//Value of text_output dynamically changes with user input
	public StringProperty text_output = new SimpleStringProperty();
	@FXML
    private JFXTextArea note_input;
   
    
    @FXML
    private JFXButton save;

    /**
     * method to add Notes to a selected question 
     * 
     * @param event 
     * @throws SQLException
     */
    @FXML
    void addNotes(MouseEvent event) throws SQLException {
    	
    	dbQueries.frage_selektieren(frage);
    	if (DBQueries.rs.next()) 
    		frage.setID(DBQueries.rs.getInt("idFrage"));
    	
		if (frage.getID() != 0) {
			
			Note note = new Note();
			String to_be_saved = text_output.getValue();
			note.setText(to_be_saved);

			dbQueries.notizienSpeichern(note, this.frage);
		} else {
			log.warning("No question selected!");
		}
	}
    
    /**
     * initialize
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		 note_input.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					
					//Bind the StringProperty to the input field -> automatically updated on input
					text_output.set(note_input.getText());
					System.out.println(text_output.getValue());
					
				}
		    });
		
	}
	
	
    


}
