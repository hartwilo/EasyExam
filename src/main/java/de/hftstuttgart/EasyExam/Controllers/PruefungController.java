package de.hftstuttgart.EasyExam.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.Protokoll;
import de.hftstuttgart.EasyExam.Controllers.PDFCreate;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;


public class PruefungController {

	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	UebersichtController uController = new UebersichtController();
	Protokoll protokoll = new Protokoll();

	// Database related variables
	DBQueries dbQuery = new DBQueries();

	public static String katalogName;
	
	

	@FXML
	private AnchorPane anchorPane;

	// ComboBoxes

	@FXML
	public ComboBox<String> katalogeComboBox;

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

	@FXML
	private CheckBox stellen;

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
	private Label punktZahlDetail;
	
	@FXML
	private TextField erreichtePunkte;

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
    private Button aktualisieren;

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

	@FXML
	private Button remove;

	@FXML
	private Button zueruck;
	
	@FXML
	private Button pdfErstellen;
	
	
	
	
																	/////////////// Java Methods //////////////
	
	//Show wanted questions in View Table
	public void showQuestions() throws SQLException {
		ObservableList<Frage> fragen = FXCollections.observableArrayList();
		
		//Fill DBQueries Result Set with objects from the DB
		loadQuestions();
		//Fill fragen list with objects in the Result Set
		fillList(fragen);
		//Display list contents in View Table
		showInMainTable(fragen);
		

	}
	
	
	// Fill DBQueries Result Set with the wanted questions
	public void loadQuestions() throws SQLException {

		// Get relevant data from the View for the query object
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

	// Create Frage.objs from result set and add to list
	public void fillList(ObservableList<Frage> fragen) throws SQLException {
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

			String grundlage = DBQueries.rs.getString("grundLageNiveau");
			String gut = DBQueries.rs.getString("gut");
			String sehrGut = DBQueries.rs.getString("SehrGut");

			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul, grundlage, gut, sehrGut));
		}
	}

	// Display a list of questions in main the TableView on the left of the screen
	public void showInMainTable(ObservableList<Frage> fragen) {
		
		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		
		gestellt
				.setCellFactory(CheckBoxTableCell.forTableColumn(gestellt));
		gestellt
				.setCellValueFactory(features -> new ReadOnlyBooleanWrapper(features.getValue().isGestelltbool()));
		gestellt
				.setEditable(false);
		
		frageTabelle.setItems(fragen);
		frageTabelle.setEditable(true);
		frageTabelle.setFixedCellSize(25);
	}


	// Create a frage.obj from the selected question in the View Table
	public Frage getSelected() {

		try {
			Boolean gestellt = frageTabelle.getSelectionModel().getSelectedItem().isGestelltbool();
			int id = frageTabelle.getSelectionModel().getSelectedItem().getID();
			int niv = frageTabelle.getSelectionModel().getSelectedItem().getNiveau();
			float punkte = (float) frageTabelle.getSelectionModel().getSelectedItem().getPunkte();
			String stellung = frageTabelle.getSelectionModel().getSelectedItem().getFrageStellung();
			String loesung = frageTabelle.getSelectionModel().getSelectedItem().getMusterloesung();
			String fragekatalog = frageTabelle.getSelectionModel().getSelectedItem().getFragekatalog();
			String modul = frageTabelle.getSelectionModel().getSelectedItem().getModul();
			String grundlage = frageTabelle.getSelectionModel().getSelectedItem().getGrundLageNiveau();
			String gut = frageTabelle.getSelectionModel().getSelectedItem().getGut();
			String sehrGut = frageTabelle.getSelectionModel().getSelectedItem().getSehrGut();
			String themengebiet = frageTabelle.getSelectionModel().getSelectedItem().getThemengebiet();

			return new Frage(id, stellung, loesung, niv, themengebiet, fragekatalog, punkte, gestellt, modul, grundlage,
					gut, sehrGut);

		} catch (Exception e) {
			log.warning("No question from table selected, details cant be read!");
			return null;
		}

	}
	
												////////////// FXML Methods ///////////////////
	

	@FXML /*
			 * The following method is used to read data from the Database into the
			 * TableView
			 */
	public void fragenAnzeigen(MouseEvent event) throws SQLException {
		showQuestions();

	}

	/*
	 * Upon clicking on a TableView row corresponding to a Question, said Question's
	 * details are displayed on the right side of the Screen/GUI
	 */
	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {

		Frage frage = getSelected();

		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

			frageStellungDetail.setText(frage.getFrageStellung());
			frageStellungDetail.setEditable(false);
			musterLoesungDetailliert.setText(frage.getMusterloesung());
			musterLoesungDetailliert.setEditable(false);
			punktZahlDetail.setText("   / " + frage.getPunkte());

		}

		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);

	}
	
	// Ask a question
		@FXML
		void frageStellen(MouseEvent event) throws SQLException {

			Frage frage = getSelected();
			dbQuery.frageStellen(frage, true);

			showQuestions();

		}

		// Un-Ask a question. (If ask button was clicked by mistake)
		@FXML
		void nichtStellen(MouseEvent event) throws SQLException {

			Frage frage = getSelected();
			dbQuery.frageStellen(frage, false);

			showQuestions();

		}


		@FXML // Unask all questions
		void setFalse(MouseEvent event) throws SQLException {

			dbQuery.setAllFalse();
			showQuestions();
		}

		
		@FXML /*
				 * The following method is used to fill the Cataloge ComboBox with all existing
				 * values in the database.
				 */
		public void katalogeLaden(MouseEvent event) throws SQLException {

			katalogeComboBox.setItems(dbQuery.katalogeAuslesen());

			showQuestions();

		}

		@FXML /*
				 * The following method is used to load all existing Topics from the Database
				 * into the Topic ComboBox
				 */
		public void themengebieteLaden(MouseEvent event) throws SQLException {

			if (katalogeComboBox.getValue() != null) {
				String katalog = katalogeComboBox.getValue();
				themen.setItems(dbQuery.themengebieteAuslesen(katalog));

				showQuestions();
			}
		}
		
		@FXML // Refresh Button: Show all questions
		void aktualisieren(MouseEvent event) throws SQLException {
			showQuestions();
			log.info("Refresh");
		}
		

		@FXML
		public void pdfErstellenClick(MouseEvent event) throws FileNotFoundException, DocumentException, SQLException {
			String katalogName = katalogeComboBox.getValue();

			ObservableList<Frage> fragen = FXCollections.observableArrayList();
			// Load DBQueries Result set with all asked questions
			// log.info("kat name is: " +katalogName);
			dbQuery.fragenLaden_gestellt(katalogName);

			// Fill list with questions in result set
			fillList(fragen);
			log.info(fragen.toString());

			FileChooser fc = new FileChooser();
			Window stage = pdfErstellen.getScene().getWindow();

			fc.setTitle("Save to PDF");
			fc.setInitialFileName("file name.pdf");

			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));

			File file = fc.showSaveDialog(stage);

			if (file != null) {

				String str = file.getAbsolutePath();

				try {

					Document document = new Document();
					PdfWriter.getInstance(document, new FileOutputStream(str));
					document.open();
					PDFCreate.addMetaData(document);
					PDFCreate.addTitlePage(document, fragen);
					PDFCreate.addContent(document, fragen);

					document.close();
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		}

		
		@FXML // Navigation Function - Go back to starter Screen.
		void zueruckDurchfuehrung(MouseEvent event) throws IOException {

			StartController.setWindow("StartScreen");

		}
		
		
		
		
		
		
		
		
		
													///////////////////// Not implemented/ Currently working on. ///////////////////
		
		@FXML
		void uebersichtAnzeigen(MouseEvent event) throws IOException, SQLException {

			showUebersicht();
		}
		
		// Not implemented, not nessecary with current solution
		public void showUebersicht() throws SQLException {

			try {

				ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();
				katalogName = katalogeComboBox.getValue();
				String katalog = katalogName;

				log.info("Catalog name static var is: " + katalogName);

				dbQuery.fragenLaden_gestellt(katalog);
				uController.show();

				fillList(gestellteFragen);
				// uController.fillAskedList(gestellteFragen);
				uController.displayUebersicht(gestellteFragen);

			} catch (IOException e) {
				Logger logger = Logger.getLogger(getClass().getName());
				log.warning("Not found!");
			}

		}

		// Not implemented, not nessecary with current solution
		public void overview() throws SQLException, IOException {
			dbQuery.fragenLaden_gestellt(katalogName);
			uController.show();

		}

		// BUG Questions added multiple times. Not yet properly implemented
		public void showKompetenzLevel(ObservableList<Frage> fragen) {

			Frage frage = getSelected();
			fragen.add(frage);

			grundlagenniveau.setCellValueFactory(features -> new ReadOnlyStringWrapper(frage.getGrundLageNiveau()));
			gut.setCellValueFactory(features -> new ReadOnlyStringWrapper(frage.getGut()));
			sehrGut.setCellValueFactory(features -> new ReadOnlyStringWrapper(frage.getSehrGut()));

			kompetenzlevelTabelle.setItems(fragen);
			kompetenzlevelTabelle.setEditable(true);
			kompetenzlevelTabelle.setFixedCellSize(25);
		}

		// BUG Questions added multiple times. Not yet properly implemented
		public void showKompetenzLevel(Frage frage) {

			frage = getSelected();

			grundlagenniveau.setCellValueFactory(new PropertyValueFactory<>("grundLageNiveau"));
			gut.setCellValueFactory(new PropertyValueFactory<>("gut"));
			sehrGut.setCellValueFactory(new PropertyValueFactory<>("sehrGut"));

			if (kompetenzlevelTabelle.getItems().contains(frage)) {
				kompetenzlevelTabelle.getItems().add(frage);
				kompetenzlevelTabelle.setEditable(true);
				kompetenzlevelTabelle.setFixedCellSize(25);
			}

		}

}
