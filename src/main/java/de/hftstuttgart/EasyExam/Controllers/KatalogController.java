package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class KatalogController {

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

	@FXML
	private Button refresh;

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

	////////////// DB Related Variables ////////////////////

	static DBQueries dbQuery = new DBQueries();

	// Used for katalog attribute of Frage
	public static String katalogName;

	@FXML
	private ComboBox<String> katalogComboBox;

	////////////////// Java Methods //////////////////////

	// Must be moved over to DBQueries
	// This method loads relevant question data into a ViewTable in the GUI
	public void fragenAnzeigen() throws SQLException {

		fragetabelle.setFixedCellSize(25); // TODO: Moving these kinds of View setup methods elsewhere
		ObservableList<Frage> frageListe = FXCollections.observableArrayList();

		// Load DBQueries Result Set with questions from DB
		katalogName = katalogComboBox.getValue();
		DBQueries.rs = dbQuery.alleFrageLaden(katalogComboBox.getValue());

		// TODO - Make method out of this
		while (DBQueries.rs.next()) {

			// Prepare Base variables to add to list.

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

			frageListe.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul));
		}

		// Define structure of FXML Table Cells you want to display data with

		fxcolumn_fragestellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		fxcolumn_punkte.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		fxcolumn_thema
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getThemengebiet()));
		fxcolumn_niveau.setCellValueFactory(features -> new ReadOnlyIntegerWrapper(features.getValue().getNiveau()));
		fxcolumn_musterloesung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getMusterloesung()));

		// Add all questions in list to FXML tableView -> Start displaying in ^
		// fxcolumns

		fragetabelle.setItems(frageListe);

	}

	//////////////// What the buttons actually do - FXML methods ////////////////

	@FXML
	void katalogNameLesen(ActionEvent event) {
		katalogName = katalogNameTextField.getText();
	}

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
		fragenAnzeigen(); // Reload new, updated set of data into TableView

	}

	@FXML /*
			 * Method for creating a new Catalog Table in Database, NOTE: Names of
			 * attributes must later be adapted to AZURE database
			 */
	void katalogAnlegen(MouseEvent event) throws IOException {

		StartController.setWindow("Katalogverwaltung");
	}

	@FXML /*
			 * The following method is used to fill the Catalog ComboBox with all existing
			 * values in the database.
			 */

	// Changes 25.11 -Gjergji
	private void katalogeLaden(MouseEvent event) throws SQLException {
		katalogComboBox.setItems(dbQuery.katalogeAuslesen());
	}

	@FXML /*
			 * GUI Navigation - Go to FrageErstellen screen
			 */
	void frageAnlegen(MouseEvent event) throws IOException {

		katalogName = katalogNameTextField.getText();
		StartController.setWindow("Frageverwaltung");

	}

	@FXML /*
			 * GUI Navigation - Go to AnfangsScreen screen
			 */
	void katalogSpeichern(MouseEvent event) throws IOException {

		StartController.setWindow("Startscreen");
	}

}
