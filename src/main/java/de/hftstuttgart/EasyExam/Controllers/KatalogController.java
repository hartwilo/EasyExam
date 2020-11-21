package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.DBConn;
import de.hftstuttgart.EasyExam.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class KatalogController {

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TextField katalogNameTextField;

	/*
	 * //Button functionality for editing a set of questions
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
	 * //ViewTable and its Columns
	 */
	@FXML 
	private TableView<de.hftstuttgart.EasyExam.Frage> fragetabelle;

	@FXML 
	private TableColumn<Frage, String> fxcolumn_fragestellung;

	@FXML
	private TableColumn<Frage, Number> fxcolumn_punkte;

	@FXML
	private TableColumn<Frage, String> fxcolumn_thema;

	@FXML
	private TableColumn<Frage, String> fxcolumn_niveau;


	@FXML
	private TableColumn<Frage, String> fxcolumn_musterloesung;
	
	//Database related variables//
	/*
	 * // Initialized prepared Statement which will later be passed executed // with
	 * a query
	 */
	public static PreparedStatement preparedStatement = null;

	/*
	 * // Initialized query which will later be modified and passed to prepared //
	 * statement
	 */
	public static String query = null;
	
	

	// This method loads relevant question data into a ViewTable in the GUI
	public void fragenLaden() throws SQLException {

		fragetabelle.setFixedCellSize(25); // TODO: Moving these kinds of View setup methods elsewhere
		ObservableList<Frage> frageListe = FXCollections.observableArrayList();

		// Prepare Database variables
		query = "Select * from Fragen";
		preparedStatement = DBConn.connection.prepareStatement(query);
		ResultSet ResultSet = preparedStatement.executeQuery();

		while (ResultSet.next()) { // TODO >?: Changing niveau to int

			// Prepare Base variables to add to list
			int ID = ResultSet.getInt("ID");
			String thema = ResultSet.getString("themengebiet");
			String fragestellung = ResultSet.getString("ID");
			String musterloesung = ResultSet.getString("musterLoesung");
			String niveau = ResultSet.getString("niveau");
			Double punkte = ResultSet.getDouble("punktZahl");
			Boolean istGestellt = ResultSet.getBoolean("gestellt");
			// Add Question Objects to list
			frageListe.add(new Frage(ID, thema, fragestellung, musterloesung, niveau, punkte, istGestellt));
		}

		/*
		 * // !!!!!!!!!!!!!!!!!!!!WARNING! YOU MIGHT HAVE TO MAKE FRAGE CLASS //
		 * IMPLEMENTJAVAFX PROPERTIES!!!!!!!!!!!!!!!!!!!!!!!!!
		 */

		fxcolumn_fragestellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFragestellung()));
		fxcolumn_punkte
				.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		fxcolumn_thema
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getThemengebiet()));
		fxcolumn_niveau
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getNiveau()));
		fxcolumn_musterloesung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getMusterLoesung()));

		// Add all questions in list to FXML tableView
		fragetabelle.setItems(frageListe);
	}
	
	void frageLoeschen() throws SQLException {
		
		//Select the ID of the question that was clicked on
		int ID = fragetabelle.getSelectionModel().getSelectedItem().getId();
		
		//Update Database @ selected ID
		query = "DELETE FROM fragen WHERE ID = " + ID;
		preparedStatement = DBConn.connection.prepareStatement(query);
		preparedStatement.executeUpdate();

	}
	
	//FXML Methods
	@FXML /*
			 * //This method loads relevant question data into a ViewTable in the GUI (as
			 * soon as the mouse is entered into the GUI)
			 */
	public void fragenLaden(MouseEvent event) throws SQLException {
		fragenLaden();
	}

	@FXML /*
			 * // This method deletes questions from a currently Selected question catalog
			 */
	void frageLoeschen(MouseEvent event) throws SQLException {
		frageLoeschen();
		fragenLaden(); //Reload new set of data into TableView
	}

	@FXML /*
			 * // Method for creating a new Catalog Table in Database, NOTE: Names of
			 * attributes // must later be adapted to AZURE database
			 */
	void katalogAnlegen(MouseEvent event) throws IOException {

		MainController.setWindow("KatalogErstellen");
	}

	@FXML /*
			 * // GUI Navigation - Go to FrageErstellen screen
			 */
	void frageAnlegen(MouseEvent event) throws IOException {

		MainController.setWindow("FrageErstellen");
	}

	@FXML /*
			 * // GUI Navigation - Go to AnfangsScreen screen
			 */
	void katalogSpeichern(MouseEvent event) throws IOException {

		MainController.setWindow("AnfangsScreen");
	}

}
