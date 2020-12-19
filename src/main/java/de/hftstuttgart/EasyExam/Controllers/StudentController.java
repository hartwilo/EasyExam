package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class StudentController implements Initializable{
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}
	
	Stage stage = new Stage();
	DBQueries dbQuery = new DBQueries(DBConn.connection);
	public Student selectedStudent = new Student();
	

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Student> studentenTabelle;

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
    private Button selektieren;
    
    
    @FXML
    void studentenAnzeigen(MouseEvent event) throws SQLException {  	
    	displayStudents();
   	
    }
    
	@FXML
	void studentSelektieren(MouseEvent event) {
		
		try {
			PruefungController pController = LoginController.loader.getController();
			selectedStudent = select();;
			pController.setStudent(selectedStudent);
			this.stage.toBack();
			
					
		} catch (NullPointerException e) {
			log.warning("No student selected from TableView "
					+e.getMessage() + e.getCause());

		}
	}
    
	
	public Student select() {
		
		
		try {
			
			int matnr = studentenTabelle.getSelectionModel().getSelectedItem().getMatrikelnr();
			int semester = studentenTabelle.getSelectionModel().getSelectedItem().getSemester();
			String vorname = studentenTabelle.getSelectionModel().getSelectedItem().getVorname();
			String nachname = studentenTabelle.getSelectionModel().getSelectedItem().getNachname();
			String studiengang = studentenTabelle.getSelectionModel().getSelectedItem().getStudiengang();
			
			selectedStudent.setMatrikelnr(matnr);
			selectedStudent.setNachname(nachname);
			selectedStudent.setVorname(vorname);
			selectedStudent.setSemester(semester);
			selectedStudent.setStudiengang(studiengang);

			return selectedStudent;
			
		} catch (NullPointerException e) {
			log.info("No student selected " + e.getCause());
			
			return selectedStudent;
		}
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
		studentenTabelle.setFixedCellSize(25);
		
		studentenTabelle.setItems(studenten);
		
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
		 * log.info(""); } catch (SQLException e) { e.printStackTrace(); } } });
		 */

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
