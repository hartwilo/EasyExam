package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.Student;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentController {
	
	Stage stage = new Stage();
	DBQueries dbQuery = new DBQueries(DBConn.connection);

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Student> fragetabelle;

    @FXML
    private TableColumn<Student, Number > fxcolumn_matnr;

    @FXML
    private TableColumn<Student, String> fxcolumn_vorname;

    @FXML
    private TableColumn<Student, String> fxcolumn_nachname;

    @FXML
    private TableColumn<Student, Number> fxcolumn_semester;

    @FXML
    private TableColumn<Student, String > fxcolumn_studiengang;
    
    
    

    @FXML
    void studentenAnzeigen(MouseEvent event) throws SQLException {
    	
    	displayStudents();
    	
    	
    	
    	
    	
    	
    }
    
    public void displayStudents() throws SQLException {
		ObservableList<Student> studenten = FXCollections.observableArrayList();
		dbQuery.studentenLaden();

		fillList(studenten);


		showInTable(studenten); 
		
		
	}

	// Fill an observable list with questions from the DBQueries Result Set
	public void fillList(ObservableList<Student> studenten) throws SQLException {
		while (DBQueries.rs.next()) {
			// Prep variables for Frage constructor
			int matnr = DBQueries.rs.getInt("Matrikelnr");
			String vorname = DBQueries.rs.getString("vorname");
			String nachname = DBQueries.rs.getString("Nachname");
			String studiengang = DBQueries.rs.getString("Studiengang");
			int semester = DBQueries.rs.getInt("Semester");

			studenten.add(new Student(matnr, vorname, nachname, semester, studiengang));
		}
	}

	// Set up table and columns and start displaying list
	public void showInTable(ObservableList<Student> studenten) {
		fxcolumn_matnr
				.setCellValueFactory(new PropertyValueFactory<>("matrikelnr"));
		fxcolumn_vorname
				.setCellValueFactory(new PropertyValueFactory<>("vorname"));
		fxcolumn_nachname
				.setCellValueFactory(new PropertyValueFactory<>("nachname"));
		fxcolumn_semester
				.setCellValueFactory(new PropertyValueFactory<>("semester"));
		fxcolumn_studiengang
				.setCellValueFactory(new PropertyValueFactory<>("studiengang"));
		fragetabelle.setFixedCellSize(25);
		fragetabelle.setItems(studenten);
	}
	
	
	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/Studenten.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		// Stage stage = new Stage();
		stage.setTitle("Übersicht - Studenten ");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();

		// A possible substitute for "on mouse entered" FXML method
		/*
		 * stage.setOnShowing(new EventHandler<WindowEvent>() {
		 * 
		 * @Override public void handle(WindowEvent event) { try { uebersicht();
		 * log.info("breh"); } catch (SQLException e) { e.printStackTrace(); } } });
		 */

	}

}
