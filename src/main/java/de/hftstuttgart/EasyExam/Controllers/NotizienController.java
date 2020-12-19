package de.hftstuttgart.EasyExam.Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    @FXML
    void addNotes(MouseEvent event) throws SQLException {
    	
		if (note_input.getText() != null) {
			
			Note note = new Note();
			String to_be_saved = text_output.getValue();
			System.out.println(to_be_saved);
			note.setText(to_be_saved);

			dbQueries.notizienSpeichern(note, this.frage);
		}
	}
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		 note_input.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					
					text_output.set(note_input.getText());
					System.out.println(text_output.getValue());
					
				}
		    });
		
	}
	
	
    


}
