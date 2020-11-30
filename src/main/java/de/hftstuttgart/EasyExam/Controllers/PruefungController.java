package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.google.j2objc.annotations.ReflectionSupport.Level;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.util.Callback;

public class PruefungController {

	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	UebersichtController uController = new UebersichtController();

	// Database related variables
	DBQueries dbQuery = new DBQueries();

	public static String katalogName;
	
	private SimpleStringProperty kat = new SimpleStringProperty();

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

	// MainView Table (Left side of screen)

	@FXML
	private TableView<Frage> frageTabelle;

	@FXML
	private TableColumn<Frage, String> frageStellung;

	@FXML
	private TableColumn<Frage, Boolean> gestellt;

	// Kompetenzlevel Table

	@FXML
	private TableView<Frage> kompetenzlevelTabelle;

	@FXML
	private TableColumn<Frage, String> grundlagenniveau;

	@FXML
	private TableColumn<Frage, String> gut;

	@FXML
	private TableColumn<Frage, String> sehrGut;

	// Buttons

	@FXML
	private Button refresh;

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	private Button start;

	@FXML
	private Button studentenLaden;

	@FXML
	private Button frageStellen;

	@FXML
	private Button setFalse;

	@FXML
	private Button uebersicht;

	/*
	 * The following method is used to read data from the Database into the
	 * TableView
	 */
	@FXML
	public void fragenAnzeigen(MouseEvent event) throws SQLException {
		
		// The list will be filled with Frage.objs
		ObservableList<Frage> fragen = FXCollections.observableArrayList();

		fragenAuslesen();
		fillList(fragen);
		showInMainTable(fragen);
		showKompetenzLevel(fragen);

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
		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);
	}

	@FXML /*
			 * The following method is used to fill the Cataloge ComboBox with all existing
			 * values in the database.
			 */
	public void katalogeLaden(MouseEvent event) throws SQLException {
		katalogeComboBox.setItems(dbQuery.katalogeAuslesen());
		

	}

	// Navigation Function - Go back to starter Screen.
	@FXML
	void zueruckDurchfuehrung(MouseEvent event) throws IOException {

		StartController.setWindow("StartScreen");

	}

	@FXML
	void frageStellen(MouseEvent event) throws SQLException {
		int id = frageTabelle.getSelectionModel().getSelectedItem().getID();
		String stellung = frageTabelle.getSelectionModel().getSelectedItem().getFrageStellung();
		String loesung = frageTabelle.getSelectionModel().getSelectedItem().getMusterloesung();
		int niv = frageTabelle.getSelectionModel().getSelectedItem().getNiveau();
		float punkte = (float) frageTabelle.getSelectionModel().getSelectedItem().getPunkte();
		String fragekatalog = frageTabelle.getSelectionModel().getSelectedItem().getFragekatalog();
		String modul = "tbd";
		Boolean gestellt = frageTabelle.getSelectionModel().getSelectedItem().isGestelltbool();

		String grundlage = frageTabelle.getSelectionModel().getSelectedItem().getGrundLageNiveau();
		String gut = frageTabelle.getSelectionModel().getSelectedItem().getGut();
		String sehrGut = frageTabelle.getSelectionModel().getSelectedItem().getSehrGut();
		String themengebiet = frageTabelle.getSelectionModel().getSelectedItem().getThemengebiet();

		Frage frage = new Frage(id, stellung, loesung, niv, themengebiet, fragekatalog, punkte, gestellt, modul,
				grundlage, gut, sehrGut);

		dbQuery.frageStellen(frage);

	}

	@FXML
	void setFalse(MouseEvent event) throws SQLException {
		dbQuery.setAllFalse();
	}

	@FXML
	void uebersichtAnzeigen(MouseEvent event) throws IOException, SQLException {
		showUebersicht();
	}

	public void showUebersicht() throws SQLException {

		try {
			
			ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();
			katalogName = katalogeComboBox.getValue();
			
			log.info("Catalog name is:" +katalogName);
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/GUI/Uebersicht.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("New Window");
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.show();

			uController.fillAskedList(gestellteFragen);
			uController.displayUebersicht(gestellteFragen);

		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			log.warning("Not found!");
		}

	}

	public void showKompetenzLevel(ObservableList<Frage> fragen) {

		grundlagenniveau
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getGrundLageNiveau()));
		gut
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getGut()));
		sehrGut
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getSehrGut()));
		kompetenzlevelTabelle.setItems(fragen);
		kompetenzlevelTabelle.setEditable(true);
		kompetenzlevelTabelle.setFixedCellSize(25);
	}

	// Display a list of questions in main the TableView on the left of the screen
	public void showInMainTable(ObservableList<Frage> fragen) {
		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		gestellt.setCellFactory(CheckBoxTableCell.forTableColumn(gestellt));

		/* for (Boolean) */

		/*
		 * gestellt.setCellFactory(CheckBoxTableCell.forTableColumn((Callback<Integer,
		 * ObservableValue<Boolean>>) gestellt)); gestellt.setCellValueFactory(features
		 * -> new ReadOnlyBooleanWrapper(features.getValue().isGestelltbool()));
		 */

		gestellt.setEditable(true);
		frageTabelle.setItems(fragen);
		frageTabelle.setEditable(true);
		frageTabelle.setFixedCellSize(25);
	}

	public void fragenAuslesen() throws SQLException {
		// Get relevant data from View
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

		// Select based on level and topic
		if (themengebiet != null && !nivalle.isSelected()) {
			dbQuery.frageLaden_niveau_themengebiet(niv, themengebiet, katalog);
			// Select based on topic
		} else if (themengebiet != null && nivalle.isSelected()) {
			dbQuery.frageLaden_themengebiet(themengebiet, katalog);
			// Select based on level
		} else if (themengebiet == null && !nivalle.isSelected()) {
			dbQuery.frageLaden_niveau(niv, katalog);
			// Select all
		} else {
			dbQuery.alleFrageLaden(katalog);
		}
	}

	public void fillList(ObservableList<Frage> fragen) throws SQLException {
		// Create Frage.objs from result set and add to list
		while (DBQueries.rs.next()) {
			// Prep variables for Frage constructor
			int ID = DBQueries.rs.getInt("idFrage");
			String thema = DBQueries.rs.getString("Themengebiet");
			String fragestellung = DBQueries.rs.getString("Fragestellung");
			String musterloesung = DBQueries.rs.getString("Musterloesung");
			int niveau = DBQueries.rs.getInt("Niveau");
			Float punkte = DBQueries.rs.getFloat("Punkte");
			Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
			String modul = DBQueries.rs.getString("Modul");
			String fragekatalog = DBQueries.rs.getString("Fragekatalog");

			String grundlage = DBQueries.rs.getString("grundlagenniveau");
			String gut = DBQueries.rs.getString("gut");
			String sehrGut = DBQueries.rs.getString("SehrGut");

			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul, grundlage, gut, sehrGut));
		}
	}

}
