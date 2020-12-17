package de.hftstuttgart.EasyExam.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Main.Main;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.PDFCreate;
import de.hftstuttgart.EasyExam.Models.Protokoll;
import de.hftstuttgart.EasyExam.Models.Student;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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


public class PruefungController implements Initializable {

	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}
	
	StudentController sController = new StudentController();
	UebersichtController uController = new UebersichtController();
	Protokoll protokoll = new Protokoll();

	// Database related variables
	DBQueries dbQuery = new DBQueries(DBConn.connection);

	public static String katalogName; //remove ?
	public static ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();
		

	public StringProperty vName = new SimpleStringProperty();
	public StringProperty nName = new SimpleStringProperty();
	public	StringProperty mNr = new SimpleStringProperty();
	
	

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

	@FXML TextField studName;

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

    @FXML // Old Refresh Button
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
   
    @FXML
    private MenuItem FragekatalogErstellen;

    @FXML
    private MenuItem StatistikAnsehen;
    
    @FXML
    private JFXToggleButton ask_switch;
    
    // JFoenix Compontents
    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
	
    
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

			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog,
						 punkte, istGestellt, modul, grundlage, gut, sehrGut));
																														
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

	//Fill view with a questions details
	public void showDetails(Frage frage, ObservableList<Frage> kompetenzStufe) {
		
		frageStellungDetail.setText(frage.getFrageStellung());
		frageStellungDetail.setEditable(false);
		musterLoesungDetailliert.setText(frage.getMusterloesung());
		musterLoesungDetailliert.setEditable(false);
		punktZahlDetail.setText("   / " + frage.getPunkte());

		grundlagenniveau.setCellValueFactory(new PropertyValueFactory<>("grundLageNiveau"));
		gut.setCellValueFactory(new PropertyValueFactory<>("gut"));
		sehrGut.setCellValueFactory(new PropertyValueFactory<>("sehrGut"));

		kompetenzStufe.add(frage);
		kompetenzlevelTabelle.setItems(kompetenzStufe);

		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);
	}
	

	//Returns true if the overview window is being shown and false otherwise
	public boolean uebersichtIsShowing() {
		if (UebersichtController.stage.isShowing()) return true;
		else return false;
	
	}
	
	public String getFilePath() {
		
		
		
		// Set default path to //Set default path to 'C:\Users\...\
		String defaultPath = System.getProperty("user.home");
		File userDirectory = new File(defaultPath);
		
		// If not found set to c:\......\
		if(!userDirectory.canRead()) {
		    userDirectory = new File("c:/");
		}
		
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(userDirectory);
		chooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Excel files", "*.xlsx") ); //"CSV files (*.csv)", "*.csv"
		File selected = chooser.showOpenDialog(Main.mainWindow);	
		try {
			String path = selected.getPath();
			log.info(path);	
			return path;
		} catch (Exception e) {
			log.warning("Path not found" +System.lineSeparator());
			e.printStackTrace();
			return null;
		}
		
	}

	public ObservableList<Student> readFromXlsx(String xlsxPath) throws SQLException, IOException {
		
		ObservableList<Student> studenten = FXCollections.observableArrayList();
		int listIndex = 0;
				
		
			// Select file with a File Choser
			
			// Create workbook object 
			Workbook workbook = new XSSFWorkbook(xlsxPath);
			// Create a sheet in the workbook
			Sheet firstSheet = workbook.getSheetAt(0);
			// Iterateor for the exel table in the sheet
			Iterator<Row> rowIterator = firstSheet.iterator();
			
			// Skip head row 
			//rowIterator.next(); 
			
			
			//Iterate the rows of the exel file
			while (rowIterator.hasNext()) {
				log.info("Iterating new row......");
				Student student = new Student();
				Row nextRow = rowIterator.next();	
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				
				
				int iteration = 0;
			
			//Go through cells of a single row - Asign cell value to student.obj attributes
				log.info("Iterating cells.....");
				while (cellIterator.hasNext()) {
					
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();
					
					switch (columnIndex) {
					case 0:
						int matrikelnr = (int) nextCell.getNumericCellValue();
						student.setMatrikelnr(matrikelnr);
						break;
					case 1:
						String vorname = nextCell.getStringCellValue();
						student.setVorname(vorname);
						break;
					case 2:
						String nachname = nextCell.getStringCellValue();
						student.setNachname(nachname);
						break;
					case 3:
						int semester = (int) nextCell.getNumericCellValue();
						student.setSemester(semester);
						break;
					case 4:
						String studiengang = nextCell.getStringCellValue();
						student.setStudiengang(studiengang);
						break;
					}
					
					
					log.info("Iteration  " + iteration + " : " + student.getMatrikelnr() + " " + student.getVorname()
										+ " " + student.getNachname() + " " + student.getSemester() + " "
												+ student.getStudiengang());
					 
	
					iteration++;
				}
				log.info("Adding to Observablelist : " + student.toString());
				studenten.add(student);

			}
			// Add the student to the list	
			
			for (int i = studenten.size(); i > 0; i--) {
				
				log.info("List content at index " + listIndex + " : " + studenten.get(listIndex).toString());
				listIndex++;
			}
			
			workbook.close();
			return studenten;

	}
	
	public void print() {
		System.out.println("Controller pruefung");
	}
	
												////////////// FXML Methods ///////////////////
	
	
	@FXML
	void showSideMenu(MouseEvent event) {
		/*
		 * HamburgerBackArrowBasicTransition transition = new
		 * HamburgerBackArrowBasicTransition(hamburger); transition.setRate(-1);
		 * 
		 * transition.setRate(transition.getRate() * (-1)); transition.play();
		 */
		if (drawer.isShown() || drawer.isShowing()) {
			drawer.close();
		} else {
			drawer.open();
		}

	}

    @FXML
	public void import_xlsx(MouseEvent event) throws SQLException, IOException {
		String xlsxPath = getFilePath();
		ObservableList<Student> studenten = readFromXlsx(xlsxPath);
		dbQuery.studentenSpeichern(studenten);
	}

	@FXML
	public void studentSelektieren(MouseEvent event) throws IOException {

		try {
			
			sController.show();
			Student student = sController.select();
			//log.info("pCon - " +student.toString());		
			setStudent(student);
		} catch (Exception e ) {
			e.printStackTrace();
		}

	}
	
	/*
	 * @FXML public void studentSelektieren2(MouseEvent event) throws IOException {
	 * FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("/GUI/Studenten.fxml"));
	 * 
	 * try {
	 * 
	 * Parent root = (Parent) loader.load(); StudentController sController =
	 * loader.getController();
	 * 
	 * Stage stage = new Stage(); stage.setScene(new Scene(root)); stage.show();
	 * Student student = sController.select(); log.info("pCon - " +
	 * student.toString());
	 * 
	 * setStudent(student); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
    
    public void setStudent(Student student) {
    	
    	String name_nachname = student.getVorname() + " "
    						+  student.getNachname();
    	String matnr = String.valueOf(student.getMatrikelnr());
    	
    	vName.setValue(name_nachname);
    	mNr.setValue(matnr);
    	
    	studName.textProperty().bind(vName);
    	matNr.textProperty().bind(mNr);
	
    	
    }
	

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
		Boolean asked = frage.isGestelltbool();
		
	
		
		ObservableList<Frage> kompetenzStufe = FXCollections.observableArrayList();

		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
			showDetails(frage, kompetenzStufe);
			ask_switch.setSelected(asked);
			
		}
	

	}
	
	
	@FXML
	void ask_unas2k(MouseEvent event) {
		Frage frage = getSelected();
		
		
		
		
		/*
		 * if (asked) { ask_switch.setSelected(); try { dbQuery.frageStellen(frage,
		 * false); } catch (Exception e) { log.warning("Question couldn't be un-asked: "
		 * + e.getMessage() + e.getCause()); } } else { try {
		 * dbQuery.frageStellen(frage, true); } catch (Exception e) {
		 * log.warning("Question couldn't be asked: " + e.getMessage() + e.getCause());
		 * } }
		 */
	  
	  }
	 
	
		@FXML
		void ask_unask(MouseEvent event) {
			Frage frage = getSelected();
			Boolean asked = frage.isGestelltbool();

			if (asked) {
				try {
					unask();
					ask_switch.setSelected(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					ask();
					ask_switch.setSelected(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	 
	
	 
	 public void ask() throws SQLException {
		 Frage frage = getSelected();
			dbQuery.frageStellen(frage, true);
			showQuestions(); 
	 }
	
	 public void unask() throws SQLException {
		 Frage frage = getSelected();
			dbQuery.frageStellen(frage, false);
			showQuestions();
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
			
			ObservableList<String> kataloge = dbQuery.katalogeAuslesen();
			katalogeComboBox.setItems(kataloge);
			showQuestions();

		}

		@FXML /*
				 * The following method is used to load all existing Topics from the Database
				 * into the Topic ComboBox
				 */
		public void themengebieteLaden(MouseEvent event) throws SQLException {

			if (katalogeComboBox.getValue() != null) {
				
				String katalog = katalogeComboBox.getValue();
				ObservableList<String> themengebiete = dbQuery.themengebieteAuslesen(katalog);
				themen.setItems(themengebiete);

				showQuestions();
			}
		}
		
		@FXML // Refresh Button: Show all questions
		void aktualisieren(MouseEvent event) throws SQLException {
			showQuestions();
			log.info("Refresh");
		}
		
		public void showUebersicht() throws SQLException, IOException {

			katalogName = katalogeComboBox.getValue();
			uController.show();

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
			//log.info(fragen.toString());

			FileChooser fc = new FileChooser();
			Window stage = pdfErstellen.getScene().getWindow();

			fc.setTitle("Save to PDF");
			fc.setInitialFileName("file name.pdf");

			fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));

			File file = fc.showSaveDialog(stage);

			if (file != null) {

				String path = file.getAbsolutePath();	

				try {

					Document document = new Document();
					FileOutputStream fos = new FileOutputStream(path);
					PdfWriter.getInstance(document,fos);
					document.open();
					PDFCreate.addMetaData(document);
					PDFCreate.addTitlePage(document, fragen);
					PDFCreate.addContent(document, fragen);

					document.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		}

		
		@FXML // Navigation Function - Go back to starter Screen.
		void zueruckDurchfuehrung(MouseEvent event) throws IOException {
			StartController.setWindow("StartScreen");

		}
		
		@FXML // GUI Navigation - Go to Pruefung starten screen
		void FragekatalogErstellenClick(ActionEvent event) {
			try {
				StartController.setWindow("Katalogverwaltung");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@FXML // GUI Navigation - Go to StatistikAnsehen screen (SOON)
		void StatistikAnsehenClick(ActionEvent event) {
//			try {
//				StartController.setWindow("StatistikAnsehen");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		
		@FXML // Shows the overview if it isn't already showing, brings it to the front if the button is clicked after it's showing
		void uebersichtAnzeigen(MouseEvent event) throws IOException, SQLException {
			
			if(!uebersichtIsShowing()) {
				showUebersicht();
			} else if (!UebersichtController.stage.isFocused()) {
				UebersichtController.stage.toFront();
			}
			
		}
		
	
		
											///////////////////// Not implemented/ Currently working on. ///////////////////
		
	
		
															/////////////// Imp. func. Testing ///////////////////
		
		//The test button and method are used for debugging and testing features 
		
		@FXML
	    private Button test;
		
	    @FXML
	    void test(MouseEvent event) throws SQLException {
	    	katalogName = katalogeComboBox.getValue();
	    	dbQuery.fragenLaden_gestellt(katalogName);
	    }


		@Override
		public void initialize(URL location, ResourceBundle resources) {
			/*
			 * boolean asked = ask_switch.selectedProperty().getValue();
			 * ask_switch.selectedProperty().addListener((InvalidationListener) new
			 * ChangeListener<JFXToggleButton>() {
			 * 
			 * @Override public void changed(ObservableValue<? extends JFXToggleButton> ov,
			 * JFXToggleButton o, JFXToggleButton n) { try { dbQuery.frageStellen() } }
			 * 
			 * });
			 */
		}

}
