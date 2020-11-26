package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class KatalogController {
	
	private static final Logger log;
	
	static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(DBConn.class.getName());
    }

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	public TextField katalogNameTextField;

	/*
	 * Button functionality for editing a set of questions
	 * 
	 */
	
	@FXML
	public Button frageAnlegen;
	
	@FXML
	public Button frageLoeschen;

	@FXML
	public Button katalogSpeichern;

	@FXML
	public Button katalogAnlegen;
	
	/*
	 * ViewTable and its Columns
	 * 
	 */
	
	@FXML 
	private TableView<de.hftstuttgart.EasyExam.Models.Frage> fragetabelle;

	@FXML 
	private TableColumn<Frage, String> fxcolumn_fragestellung;

	@FXML
	private TableColumn<Frage, Number> fxcolumn_punkte;

	@FXML
	private TableColumn<Frage, String> fxcolumn_thema;

	@FXML
	private TableColumn<Frage, Number> fxcolumn_niveau;


	@FXML
	private TableColumn<Frage, String> fxcolumn_musterloesung;
	
	//////////////  DB Related Variables ////////////////////
	
	/*
	 *  Initialized prepared Statement which will later be passed executed with
	 *  a query
	 */
	public static PreparedStatement preparedStatement = null;

	/*
	 * // Initialized query which will later be modified and passed to prepared //
	 * statement
	 */
	
	DBQueries dbQuery = new DBQueries();
	
	public static String query = null;
	
	//Used for katalog attribute of Frage
	public static String katalogName;
	
	
	@FXML
    private ComboBox<String> katalogComboBox;

	
	
	
	//////////////////  Java Methods  //////////////////////

	//Must be moved over to DBQueries
	// This method loads relevant question data into a ViewTable in the GUI
	public void fragenAnzeigen() throws SQLException {
		
		/*
		 * //Changes 25.11 - Gjergji - Only load questions of the selected Catalog; If
		 * none selected then load all
		 * 
		 * 
		 * KatalogNames can't contain spaces!!!
		 * Unkown column Test3 error? test3 should be the attribute not the column(name)
		 * 
		 * 
		 * 
		 */			
		
		/* TODO - Load according to catalog name
		 * 
		 * if (!katalogNameTextField.getText().isBlank()) { katalogName =
		 * katalogNameTextField.getText(); query =
		 * "Select * from Frage where Fragekatalog = "+ katalogName; } else if
		 * (katalogComboBox.getValue() != null) { katalogName =
		 * katalogComboBox.getValue(); query =
		 * "Select * from Frage where Fragekatalog = "+ katalogName; } else { query =
		 * "Select * from Frage"; }
		 */
		
		fragetabelle.setFixedCellSize(25); // TODO: Moving these kinds of View setup methods elsewhere
		ObservableList<Frage> frageListe = FXCollections.observableArrayList();

		// Prepare Database variables
		
		//Changes 25.11 - Only load questions of Katalog
		//query = "Select * from Frage where katalog = " + katalogName;
		//query = "Select * from Frage";
		
		//TODO - Delete or figure out how to print the query of the DBQueries load method
		//log.info(query)
		
		/* Replaced by method in DBQueries
		 * preparedStatement = DBConn.connection.prepareStatement(query); ResultSet
		 * ResultSet = preparedStatement.executeQuery();
		 */
		
		//Load DBQueries Result Set with questions from DB
		DBQueries.rs = dbQuery.alleFrageLaden();
		

		while (DBQueries.rs.next()) { 

			
			//Prepare Base variables to add to list.	
			
			int ID = DBQueries.rs.getInt("idFrage");										
			String thema = DBQueries.rs.getString("Themengebiet");
			String fragestellung = DBQueries.rs.getString("Fragestellung");
			String musterloesung = DBQueries.rs.getString("Musterloesung");
			int niveau = DBQueries.rs.getInt("Niveau");
			Float punkte = DBQueries.rs.getFloat("Punkte");
			Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
			String modul = DBQueries.rs.getString("Modul");
			String fragekatalog = DBQueries.rs.getString("Fragekatalog");			
			
			// Add Question Objects to list
			
			frageListe.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt, modul));
		}

		// Define structure of FXML Table Cells you want to display data with
		
		fxcolumn_fragestellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		fxcolumn_punkte
				.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		fxcolumn_thema
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getThemengebiet()));
		fxcolumn_niveau
				.setCellValueFactory(features -> new ReadOnlyIntegerWrapper(features.getValue().getNiveau()));
		fxcolumn_musterloesung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getMusterloesung()));

		// Add all questions in list to FXML tableView -> Start displaying in ^ fxcolumns
		
		fragetabelle.setItems(frageListe);
		
		
	}
	
	/*   !Replaced by frageLoeschen method in DBQueries class
	 * 
	 * void frageLoeschen() throws SQLException {
	 * 
	 * //Select the ID of the question that was clicked on int ID =
	 * fragetabelle.getSelectionModel().getSelectedItem().getID();
	 * 
	 * //Update Database @ selected ID query = "DELETE FROM Frage WHERE idFrage = "
	 * + ID; log.info(query); preparedStatement =
	 * DBConn.connection.prepareStatement(query); preparedStatement.executeUpdate();
	 * }
	 */
	
	////////////////      What the buttons actually do - FXML methods     ////////////////
	
	@FXML /*
			 * This method loads relevant question data into a ViewTable in the GUI (as soon
			 * as the mouse is entered into the GUI)
			 */
	public void fragenLaden(MouseEvent event) throws SQLException {
		fragenAnzeigen();

	}

	@FXML /*
			 * This method deletes questions from a currently Selected question catalog
			 */
	void frageLoeschen(MouseEvent event) throws SQLException {
		int ID = fragetabelle.getSelectionModel().getSelectedItem().getID();
		dbQuery.frageLoeschen(ID);
		fragenAnzeigen(); 	//Reload new, updated set of data into TableView

	}

	@FXML /*
			 * Method for creating a new Catalog Table in Database, NOTE: Names of
			 * attributes must later be adapted to AZURE database
			 */
	void katalogAnlegen(MouseEvent event) throws IOException {

		StartController.setWindow("Katalogverwaltung");
	}
	
	@FXML/*
	 *  The following method is used to fill the Catalog ComboBox with all existing
	 *  values in the database.
	 */
	
	//Changes 25.11 -Gjergji
	private ObservableList<String> katalogeLaden(MouseEvent event) throws SQLException {


		katalogComboBox.setItems(dbQuery.katalogeAuslesen());

		return dbQuery.katalogeAuslesen();
	}

	@FXML /*
			 * GUI Navigation - Go to FrageErstellen screen
			 */
	void frageAnlegen(MouseEvent event) throws IOException {

		StartController.setWindow("Frageverwaltung");
	}

	@FXML /*
			 * GUI Navigation - Go to AnfangsScreen screen
			 */
	void katalogSpeichern(MouseEvent event) throws IOException {

		StartController.setWindow("Startscreen");
	}

}
