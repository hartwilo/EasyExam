package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class KatalogController {
	
	private static final Logger log;
	
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
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
    private Button katalogLoeschen;
	
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
	
	@FXML
	private ComboBox<String> katalogComboBox;
	
	static DBQueries dbQuery = new DBQueries(DBConn.connection);
	public static String katalogName;
	
	
	
//////////////////Java Methods //////////////////////


// This method loads relevant question data into a ViewTable in the GUI
	public void fragenAnzeigen() throws SQLException {

		ObservableList<Frage> frageListe = FXCollections.observableArrayList();

// Load DBQueries Result Set with questions from DB
		katalogName = katalogComboBox.getValue();
		DBQueries.rs = dbQuery.alleFrageLaden(katalogName);

		fillList(frageListe);
		showInMainTable(frageListe);

	}

	public void fillList(ObservableList<Frage> fragen) throws SQLException {
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
			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul));
		}
	}
	
	
// Define structure of FXML Table Cells you want to display data with
	public void showInMainTable(ObservableList<Frage> fragen) {
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
		
		fragetabelle.setFixedCellSize(25);
		fragetabelle.setItems(fragen);
	}
	
//Evt Warning-Klasse anlegen	
		private void warnungAnzeigen(String warnung) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText(warnung);
			alert.showAndWait();
		}


//////////////// FXML methods ////////////////

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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("Möchten Sie die Frage wirklich löschen?");
		
		Optional<ButtonType> ok = alert.showAndWait();

		if (ok.get() == ButtonType.OK) {
			dbQuery.frageLoeschen(ID);
		}

		fragenAnzeigen(); // Reload new, updated set of data into TableView

	}

	@FXML /*
			 * The following method is used to fill the Catalog ComboBox with all existing
			 * values in the database.
			 */
	private void katalogeLaden(MouseEvent event) throws SQLException {
		katalogComboBox.setItems(dbQuery.katalogeAuslesen());
		fragenAnzeigen(); 
	}

	@FXML /* Delete the selected catalog of questions */
	void katalogLoeschen(MouseEvent event) throws SQLException {
		if (katalogNameTextField.getText().isEmpty()) {
			katalogName = katalogComboBox.getValue();
		} else {
			katalogName = katalogNameTextField.getText();	
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("Der Katalog " + katalogName + " wird gelöscht");
		// Only delete if ok is clicked
		Optional<ButtonType> ok = alert.showAndWait();
		
		if (ok.get() == ButtonType.OK) {
			dbQuery.katalogLoeschen(katalogName);
		}
		
		
		fragenAnzeigen(); // Reload new, updated set of data into TableView ??
	}

	
	@FXML /*
			 * Method currently not used
			 */
	void katalogAnlegen(MouseEvent event) throws IOException {
		
		StartController.setWindow("Katalogverwaltung");
	}

	@FXML /*
			 * Add a question to the selected catalog. If none is selected a new catalog name must be provided in the relevant TextArea.
			 */
	void frageAnlegen(MouseEvent event) throws IOException, SQLException {
		
		if(katalogNameTextField.getText().isEmpty() && katalogComboBox.getValue()==null) {
			warnungAnzeigen("Bitte Fragekatalog auswählen!");
			System.out.println("zweig1");
		}
		else if (katalogComboBox.getValue()==null) {
			katalogName = katalogNameTextField.getText();
			System.out.println("zweig2");
			log.info("Adding question to: "+katalogName);
			StartController.setWindow("Frageverwaltung");
		}
		else if (katalogNameTextField.getText().isEmpty()) {
			katalogName = katalogComboBox.getValue();
			System.out.println("zweig3");
			log.info("Adding question to: "+katalogName);
			StartController.setWindow("Frageverwaltung");
		}
		else if(katalogComboBox.getItems().contains(katalogNameTextField.getText())){
		katalogName = katalogComboBox.getValue();
		System.out.println("zweig4");
		log.info("New Catalog creation in progress. Save new question to create "+katalogName);
		log.info("Adding question to: "+katalogName);
		StartController.setWindow("Frageverwaltung");
	} else {
		warnungAnzeigen("Ausgewählten Fragekatalog bitte überprüfen!");
	}
		
	}

	@FXML /*
			 * GUI Navigation - Save all current changes and go back to the start screen
			 */
	void katalogSpeichern(MouseEvent event) throws IOException {

		StartController.setWindow("Startscreen");
	}

	@FXML
	void katalogNameLesen(ActionEvent event) {
		katalogName = katalogNameTextField.getText();
	}	
		

}
