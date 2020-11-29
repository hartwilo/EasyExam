package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class PruefungController {
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	// Database related variables
	DBQueries dbQuery = new DBQueries();
	

	@FXML
	private AnchorPane anchorPane;

	// ComboBoxes

	@FXML
	private ComboBox<String> katalogeComboBox;

	@FXML
	private ComboBox<String> themen;

	// CheckBoxes and RadioButtons
	@FXML
	private ToggleGroup niveau;

	@FXML
	private RadioButton niveau1;

	@FXML
	private RadioButton niveau2;

	@FXML
	private RadioButton niveau3;

	@FXML
	private RadioButton nivalle;

	// Textfields and areas
	
	@FXML
	private TextArea frageStellungDetail;

	@FXML
	private TextArea musterLoesungDetailliert;
	
	@FXML
	private TextField studName;

	@FXML
	private TextField matNr;

	@FXML
	private TextField punktZahlDetail;
	
	// View Table
	
	@FXML
	private TableView<Frage> frageTabelle;

	@FXML
	private TableColumn<Frage, String> frageStellung;

	@FXML
	private TableColumn<Frage, CheckBox> gestellt;


	// Buttons

    @FXML
    private Button refresh;
	
	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	private Button start;

	@FXML
	private Button studentenLaden;
	
	CheckBox gestelltCheckBox = new CheckBox(null);
	
	
	
	/* The following method is used to read data from the Database into the
	 * TableView
	 */
	@FXML
	public void fragenLaden(MouseEvent event) throws SQLException {
		//The list will be filled with Frage.objs
		ObservableList<Frage> fragen = FXCollections.observableArrayList();
				
		//Get relevant data from View
		String themengebiet = themen.getValue(); 
		
		String katalog = katalogeComboBox.getValue();
		
		int niv = 0;
		if (niveau1.isSelected()) {
			niv = 1;	
		} else if (niveau2.isSelected()) {
			niv = 2;
		} else if (niveau3.isSelected()) {
			niv = 3;
		} 
				
		//Select based on level and topic
		if (themengebiet != null && !nivalle.isSelected()) {
			dbQuery.frageLaden_niveau_themengebiet(niv, themengebiet, katalog);
		//Select based on topic
		} else if (themengebiet != null && nivalle.isSelected() ) {
			dbQuery.frageLaden_themengebiet(themengebiet, katalog);
		//Select based on level
		} else if (themengebiet == null && !nivalle.isSelected()) {
			dbQuery.frageLaden_niveau(niv, katalog);
		//Select all
		} else {
			dbQuery.alleFrageLadenMitKatalog(katalog);
		}

		//Create Frage.objs from result set and add to list
		while (DBQueries.rs.next()) {
			//Prep variables for Frage constructor
			int ID = DBQueries.rs.getInt("idFrage");										
			String thema = DBQueries.rs.getString("Themengebiet");
			String fragestellung = DBQueries.rs.getString("Fragestellung");
			String musterloesung = DBQueries.rs.getString("Musterloesung");
			int niveau = DBQueries.rs.getInt("Niveau");
			Float punkte = DBQueries.rs.getFloat("Punkte");
			Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
			String modul = DBQueries.rs.getString("Modul");
			String fragekatalog = DBQueries.rs.getString("Fragekatalog");
			
			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt, modul));
		}
		//Set up View Table columns
		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		gestellt.setCellFactory(features -> new CheckBoxTableCell<>());
		gestellt.setEditable(true);
		
		//Display list of Frage.objs
		frageTabelle.setItems(fragen);
		frageTabelle.setEditable(true);
		frageTabelle.setFixedCellSize(25);

	}

	/*
	 * The following method is used to load all existing Topics from the Database
	 * into the Topic ComboBox
	 */
	
	@FXML
	public void themengebieteLaden(MouseEvent event) throws SQLException {	
		if (katalogeComboBox.getValue() != null) {
		String katalog = katalogeComboBox.getValue();
		themen.setItems(dbQuery.themengebieteAuslesen(katalog));
		}
	}
	

	/*
	 * Upon clicking on a TableView row corresponding to a Question, said Question's
	 * details are displayed on the right side of the Screen/GUI
	 */
	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {

		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);

		// Selection Model - Selected Item -> Frage.obj
		String fragestellungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getFrageStellung();
		String musterloesungdetailliert = frageTabelle.getSelectionModel().getSelectedItem().getMusterloesung();
		String punktzahl = Double.toString(frageTabelle.getSelectionModel().getSelectedItem().getPunkte());

		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

			frageStellungDetail.setText(fragestellungdetailliert);
			frageStellungDetail.setEditable(false);
			musterLoesungDetailliert.setText(musterloesungdetailliert);
			musterLoesungDetailliert.setEditable(false);
			punktZahlDetail.setText(punktzahl);
			punktZahlDetail.setEditable(false);
		}
	}
	@FXML /*
	 *  The following method is used to fill the Cataloge ComboBox with all existing
	 *  values in the database.
	 */
public void katalogeLaden(MouseEvent event) throws SQLException {
		katalogeComboBox.setItems(dbQuery.katalogeAuslesen());

}

	// Navigation Function - Go back to starter Screen.
	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		StartController.setWindow("StartScreen");

	}

}
