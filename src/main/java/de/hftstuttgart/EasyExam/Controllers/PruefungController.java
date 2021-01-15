package de.hftstuttgart.EasyExam.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.PopOver;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.sun.prism.paint.Color;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Main.Main;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.Note;
import de.hftstuttgart.EasyExam.Models.PDFCreate;
import de.hftstuttgart.EasyExam.Models.Protokoll;
import de.hftstuttgart.EasyExam.Models.Student;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PruefungController implements Initializable {

	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}
	Stage noteStage = new Stage();
	StudentController sController = new StudentController();
	UebersichtController uController = new UebersichtController();
	Protokoll protokoll = new Protokoll();

	// Database related variables
	DBQueries dbQuery = new DBQueries(DBConn.connection);
	StudentController studCon = new StudentController();
	LoginController logCon = new LoginController();
	

	public static String katalogName; // remove ?
	public static String frageKatalog;
	public static ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();

	public StringProperty vName = new SimpleStringProperty();
	public StringProperty nName = new SimpleStringProperty();
	public StringProperty mNr = new SimpleStringProperty();
	
	Student studie = new Student();
	
	int selected_table_row; 

	@FXML
	private AnchorPane anchorPane;
	
	@FXML
    private Label label_musterloesung;

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
	TextField studName;

	@FXML
	private TextField matNr;

	@FXML
	private Label punktZahlDetail;

	@FXML
	private JFXTextField erreichte_punkte;

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
	private JFXButton notizien;

	@FXML // Old Refresh Button
	private Button aktualisieren;

	@FXML
	public Button zueruckDurchfuehrung;

	@FXML
	private Button start;

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

	/*
	 * @FXML private Button pdfErstellen;
	 */

	// Switches
	
	@FXML
	private ToggleGroup point_switches;
	
	@FXML
	private JFXToggleButton ask_switch;

	@FXML
	private JFXToggleButton grundlage_switch;

	@FXML
	private JFXToggleButton gut_switch;

	@FXML
	private JFXToggleButton sehrgut_switch;

	// JFoenix Compontents
	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXDrawer drawer;

	/////////////// Java Methods //////////////

	/**
	 * Show wanted questions in View Table
	 * 
	 * @throws SQLException
	 */
	public void showQuestions() throws SQLException {
		ObservableList<Frage> fragen = FXCollections.observableArrayList();

		// Fill DBQueries Result Set with objects from the DB
		loadQuestions();
		// Fill fragen list with objects in the Result Set
		fillList(fragen);
		// Display list contents in View Table
		showInMainTable(fragen);

	}

	/**
	 * Fill DBQueries Result Set with the wanted questions
	 * 
	 * @throws SQLException
	 */ 
	public void loadQuestions() throws SQLException {

		// Get relevant data from the View for the query object
		String themengebiet = themen.getValue();
		String katalog = katalogeComboBox.getValue();
		frageKatalog = katalogeComboBox.getValue();
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

	/**
	 * Create Frage.objs from result set and add to list
	 * 
	 * @param fragen
	 * @throws SQLException
	 */
	public void fillList(ObservableList<Frage> fragen) throws SQLException {

		while (DBQueries.rs.next()) {

			// Prep variables for Frage constructor
			int ID = DBQueries.rs.getInt("idFrage");
			String thema = DBQueries.rs.getString("Themengebiet");
			String fragestellung = DBQueries.rs.getString("Fragestellung");
			String musterloesung = DBQueries.rs.getString("Musterloesung");
			int niveau = DBQueries.rs.getInt("Niveau");
			Float punkte = DBQueries.rs.getFloat("Punkte");
			Float errPunkte = DBQueries.rs.getFloat("Punkte_erreicht");
			Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
			String modul = DBQueries.rs.getString("Modul");
			String fragekatalog = DBQueries.rs.getString("Fragekatalog");

			String grundlage = DBQueries.rs.getString("grundLageNiveau");
			String gut = DBQueries.rs.getString("gut");
			String sehrGut = DBQueries.rs.getString("SehrGut");

			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul, grundlage, gut, sehrGut, errPunkte));

		}
	}

	/**
	 * Display a list of questions in main the TableView on the left of the screen
	 * 
	 * @param fragen
	 */
	public void showInMainTable(ObservableList<Frage> fragen) {

		frageStellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));

		gestellt.setCellFactory(CheckBoxTableCell.forTableColumn(gestellt));
		gestellt.setCellValueFactory(features -> new ReadOnlyBooleanWrapper(features.getValue().isGestelltbool()));
		gestellt.setEditable(false);

		frageTabelle.setItems(fragen);
		frageTabelle.setEditable(true);
		frageTabelle.setFixedCellSize(25);
	}

	/**
	 * Create a frage.obj from the selected question in the View Table
	 * 
	 * @return
	 */
	public Frage get_selected_question() {

		try {
			Boolean gestellt = frageTabelle.getSelectionModel().getSelectedItem().isGestelltbool();
			int id = frageTabelle.getSelectionModel().getSelectedItem().getID();
			int niv = frageTabelle.getSelectionModel().getSelectedItem().getNiveau();
			float punkte = (float) frageTabelle.getSelectionModel().getSelectedItem().getPunkte();
			float errPunkte = (float) frageTabelle.getSelectionModel().getSelectedItem().getErreichtePunkte();
			String stellung = frageTabelle.getSelectionModel().getSelectedItem().getFrageStellung();
			String loesung = frageTabelle.getSelectionModel().getSelectedItem().getMusterloesung();
			String fragekatalog = frageTabelle.getSelectionModel().getSelectedItem().getFragekatalog();
			String modul = frageTabelle.getSelectionModel().getSelectedItem().getModul();
			String grundlage = frageTabelle.getSelectionModel().getSelectedItem().getGrundLageNiveau();
			String gut = frageTabelle.getSelectionModel().getSelectedItem().getGut();
			String sehrGut = frageTabelle.getSelectionModel().getSelectedItem().getSehrGut();
			String themengebiet = frageTabelle.getSelectionModel().getSelectedItem().getThemengebiet();
			
			//Note the last selected question from the topic
			selected_table_row = frageTabelle.getSelectionModel().getSelectedIndex();

			return new Frage(id, stellung, loesung, niv, themengebiet, fragekatalog, punkte, gestellt, modul, grundlage,
					gut, sehrGut, errPunkte);

		} catch (Exception e) {
			log.warning("No question from table selected, details cant be read!");
			return null;
		}

	}

	/**
	 * Fill view with a questions details
	 * 
	 * @param frage
	 */
	public void showDetails(Frage frage) {
		
		ObservableList<Frage> kompetenzStufe = FXCollections.observableArrayList();
		handle_point_switches();
		frageStellungDetail.setText(frage.getFrageStellung());
		frageStellungDetail.setEditable(false);
		musterLoesungDetailliert.setText(frage.getMusterloesung());
		musterLoesungDetailliert.setEditable(false);
		punktZahlDetail.setText(""+frage.getPunkte());
		erreichte_punkte.setText(String.valueOf(frage.getErreichtePunkte()));

		grundlagenniveau.setCellValueFactory(new PropertyValueFactory<>("grundLageNiveau"));
		gut.setCellValueFactory(new PropertyValueFactory<>("gut"));
		sehrGut.setCellValueFactory(new PropertyValueFactory<>("sehrGut"));

		kompetenzStufe.add(frage);
		kompetenzlevelTabelle.setItems(kompetenzStufe);

		frageStellungDetail.setWrapText(true);
		musterLoesungDetailliert.setWrapText(true);
		
		
		//System.out.println(frage.getErreichtePunkte());
	}
	
	/**
	 * Toggles the correct correct switch when already answered questions get
	 * selected again from the question table
	 * 
	 */
	public void handle_point_switches() {
		
		Frage frage = get_selected_question();
		
		try {
			Float erp = dbQuery.select_erreichte_punkte(frage);
			Float f = (float) (erp / frage.getPunkte());
			// f -> % of achieved points:

			// not answered or 0 points

			if (f <= 0.3) { // 30%
				grundlage_switch.setSelected(true);

			}  if (f >= 0.3 && f < 0.6) { // 60%
				gut_switch.setSelected(true);
			}  if (f >= 0.6) { // 60-100%
				sehrgut_switch.setSelected(true);
			} if (f == 0)  {
				System.out.print("question was not asked or 0 points achieved");
				grundlage_switch.setSelected(false);
				gut_switch.setSelected(false);
				sehrgut_switch.setSelected(false);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Returns true if the overview window is being shown and false otherwise
	 * 
	 * @return
	 */
	public boolean uebersichtIsShowing() {
		if (UebersichtController.stage.isShowing())
			return true;
		else
			return false;

	}

	/**
	 * The method imports an excel file 
	 * 
	 * @return String with path or null
	 */
	public String select_file() {

		// Set default path to //Set default path to 'C:\Users\...\
		String defaultPath = System.getProperty("user.home");
		File userDirectory = new File(defaultPath);

		// If not found set to c:\......\
		if (!userDirectory.canRead()) {
			userDirectory = new File("c:/");
		}

		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(userDirectory);
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel files", "*.xls")); // "CSV files
																										// (*.csv)",
																										// "*.csv"
		File selected = chooser.showOpenDialog(Main.mainWindow);
		try {
			String path = selected.getPath();
			log.info(path);
			return path;
		} catch (Exception e) {
			log.warning("Path not found" + e.getMessage() +" " 
			+e.getCause());
			
			return null;
		}


	}

	/**
	 * Reads all students in a .xls file and returns an observable list of these students
	 * 
	 * @param xlsPath
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public ObservableList<Student> readFromXls(String xlsPath) throws SQLException, IOException {
		
		ObservableList<Student> studenten = FXCollections.observableArrayList();

		int listIndex = 0;
		
		// Select file with a File Choser and pass it to input stream

		File xlsFile = new File(xlsPath);
		FileInputStream input_xls = new FileInputStream(xlsFile);
		
		
		// Create workbook object
		//Workbook workbook = new XSSFWorkbook(xlsxPath);
		Workbook workbook = new HSSFWorkbook(input_xls);
		// Create a sheet in the workbook
		Sheet firstSheet = workbook.getSheetAt(0);
		// Iterateor for the excel table in the sheet
		Iterator<Row> rowIterator = firstSheet.iterator();

		// Skip the rows that contain no relevant data
		for (int i = 0; i < 3; i++) {
			rowIterator.next();
		}

		// Iterate the remaining rows of the exel file
		while (rowIterator.hasNext()) {
			
			
			log.info("-->   Iterating new row......");
			Student student = new Student();
			Row nextRow = rowIterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			int iteration = 0;
			
			// Go through cells of a single row - Assign cell value to student.obj attributes
			log.info("-->  Iterating cells.....");
			while (cellIterator.hasNext()) { 
				
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();
				String cellType = nextCell.getCellType().toString();

				switch (columnIndex) {
				case 0: //Matnr Cell

					if (cellType == "NUMERIC") {
						int matrikelNr = (int) nextCell.getNumericCellValue();
						student.setMatrikelnr(matrikelNr);
					} else {

						String s = nextCell.getStringCellValue();

						if (s == "endHISsheet") {
							
							log.info("Read Last Row");
							return studenten;
							
						} else {
							
							try {
								
								int matrikelNr = Integer.valueOf(s);
								student.setMatrikelnr(matrikelNr);
								
							} catch (Exception e) {
								log.warning("Couldnt read Student!" +e.getCause());
							}
						}
					}

					break;
				case 2: //Studiengang cell
					String studiengang = nextCell.getStringCellValue();
					student.setStudiengang(studiengang);
					break;
				case 5: //Name - Nachname cell
					String name = nextCell.getStringCellValue();
					String vName = name.substring( 0, name.indexOf(","));
					String nName = name.substring(name.lastIndexOf(',') +1).trim();

					student.setVorname(vName);
					student.setNachname(nName);
					break;

				case 10: //Semester Cell

					if (cellType == "NUMERIC") {
						int semester = (int) nextCell.getNumericCellValue();
						student.setSemester(semester);
					} else {
						int semester = Integer.valueOf(nextCell.getStringCellValue());
						student.setSemester(semester);
					}


				}

				log.info("Iteration  " + iteration + " : " + student.getMatrikelnr() + " " + student.getVorname() + " "
						+ student.getNachname() + " " + student.getSemester() + " " + student.getStudiengang());

				iteration++;
			}
			
			log.info("Adding to Observablelist : " +System.lineSeparator()
			+ student.toString());
			
			//Make sure no missread students are added
			if (student.getMatrikelnr() != 0) studenten.add(student);
			
			//Close files.
			input_xls.close();
			workbook.close();
		}
		

		for (int i = studenten.size(); i > 0; i--) {

			log.info("List content at index " + listIndex + " : " + studenten.get(listIndex).toString());
			listIndex++;
		}

		workbook.close();
		return studenten;

	}
	
public void writeExcel(int note) throws IOException {
		
		double matrk = 654321;
		String xlsxPath = select_file();
		int testNote = 230;
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		
		InputStream inp = new FileInputStream(xlsxPath); 
	    Workbook wb = WorkbookFactory.create(inp); 
	    Sheet sheet = wb.getSheetAt(0); 
	    Row row;
	    
	    	for(int rowIndex = 4; rowIndex <= 15; rowIndex++) {
	    		 row = sheet.getRow(rowIndex);
	    		
	    				 if (row != null) {
	    					    Cell cell = row.getCell(0);
	    					    if (cell != null) {
	    					      // Found column and there is value in the cell.
	    					      double cellValueMaybeNull = cell.getNumericCellValue();
	    					    	//String cellValueMaybeNull = cell.getStringCellValue();
	    					    	//int matrikel = Integer.parseInt(cellValueMaybeNull);
	    					      System.out.println(cellValueMaybeNull);
	    					    	if(cellValueMaybeNull == matrk) {	    					    	 
	    					    	  Cell cellNote = row.getCell(6);
	    					    	  Cell cellDate = row.getCell(8);
	    					    	  CellStyle cellStyle = wb.createCellStyle();
	    					    	  CreationHelper createHelper = wb.getCreationHelper();
	    					    	  cellStyle.setDataFormat(
	    					    	      createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
	    					    	  System.out.println("CellNeu: "+cellNote);
	    					    	  cellNote.setCellValue(note);
	    					    	  cellDate.setCellValue(new Date());
	    					    	  cellDate.setCellStyle(cellStyle);
	    					    	  
	    					    	  System.out.println("Note: "+note);
	    					      } 
	    					      // Do something with the cellValueMaybeNull here ...
	    					      // break; ???
	    					    }
	    					  }		
	    	}
	   
	    FileOutputStream fileOut = new FileOutputStream((xlsxPath)); 
	    wb.write(fileOut); 
	    fileOut.close(); 
	    }
	
	
	
	

	////////////// FXML Methods ///////////////////

	/**
	 * show side Menu
	 * 
	 * @param event
	 */
	@FXML 
	void showSideMenu(MouseEvent event) {
		if (drawer.isShown() || drawer.isShowing()) {
			drawer.close();
		} else {
			drawer.setOverLayVisible(false);
			System.out.println(drawer.isOverLayVisible());
			drawer.open();
		}

	}

	/**
	 * import xlsx
	 * 
	 * @param event
	 * @throws SQLException
	 * @throws IOException
	 */
	@FXML
	public void import_xlsx(MouseEvent event) throws SQLException, IOException {
		String xlsxPath = select_file();
		ObservableList<Student> studenten = readFromXls(xlsxPath);
		dbQuery.studentenSpeichern(studenten);
	}

	public void studenten_importieren() throws SQLException, IOException {
		String xlsxPath = select_file();
		ObservableList<Student> studenten = readFromXls(xlsxPath);
		dbQuery.studentenSpeichern(studenten);
	}
	
	/**
	 * The method selects an student from an excel sheet 
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void studentSelektieren(MouseEvent event) throws IOException {
		
		try {
			StudentController sController = new StudentController();
			sController.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * setStudent 
	 * 
	 * @param student Student object 
	 */
	public void setStudent(Student student) {

		String name = student.getVorname();
		String nachname = student.getNachname();
		String matnr = String.valueOf(student.getMatrikelnr());

		vName.setValue(name +" "+ nachname);
		mNr.setValue(matnr);

		studName.textProperty().bind(vName);
		matNr.textProperty().bind(mNr);
		
		studie = student;
		

	}

	/**
	 * The following method is used to read data from the Database into the TableView
	 * 
	 * @param event
	 * @throws SQLException
	 */
	@FXML
	public void fragenAnzeigen(MouseEvent event) throws SQLException {
		showQuestions();

	}

	/**
	 * Upon clicking on a TableView row corresponding to a Question, 
	 * said Question's details are displayed on the right side of the Screen/GUI
	 * 
	 * @param event
	 * @throws SQLException
	 */
	@FXML
	void detailsAnzeigen(MouseEvent event) throws SQLException {
		loadQuestions();
		Frage frage = get_selected_question();
		Boolean asked = frage.isGestelltbool();
		
		showDetails(frage);
		ask_switch.setSelected(asked);

		
		if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
			if (asked) {
				try {
					unask(frage);
					ask_switch.setSelected(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					ask(frage);
					ask_switch.setSelected(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * The method asks or unasks a question 
	 * 
	 * @param event 
	 */
	@FXML //to be deleted
	void ask_unas2k(MouseEvent event) {
		Frage frage = get_selected_question();


	}

	/**
	 * The method asks or unasks a question 
	 * 
	 * @param event 
	 */
	@FXML
	void ask_unask(MouseEvent event) {
		Frage frage = get_selected_question();
		Boolean asked = frage.isGestelltbool();

		if (asked) {
			try {
				unask(frage);
				ask_switch.setSelected(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				ask(frage);
				ask_switch.setSelected(true);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * The method sets questions on asked 
	 * 
	 * @param frage Frage object 
	 * @throws SQLException
	 */
	public void ask(Frage frage) throws SQLException {
		dbQuery.frageStellen(frage, true);
		showQuestions();
	}

	/**
	 * The method sets questions on unasked 
	 * 
	 * @param frage Frage object 
	 * @throws SQLException
	 */
	public void unask(Frage frage) throws SQLException {
		dbQuery.frageStellen(frage, false);
		showQuestions();
	}
	
	/**
	 * Build PopOver look and feel
	 * 
	 * @param content that will be displayed in the popover 
	 * @return
	 */
	public PopOver buildPopOver(String content) {
		Label label = new Label(content);
		VBox vBox = new VBox(label);

		vBox.setPrefHeight(600.0);
		vBox.setPrefWidth(800.0);
		
		return new PopOver(vBox);
	}
	
	/**
	 * handle PopOver
	 * 
	 * @param event
	 */
	@FXML
	void handlePopOver_ml(MouseEvent event) {
		
		try {
			Frage frage = get_selected_question();
			PopOver popFrage = buildPopOver(frage.getMusterloesung());
			popFrage.show(label_musterloesung);

			label_musterloesung.setOnMouseExited(mouseEvent -> {
				// Hide PopOver when mouse exits label
				popFrage.hide();
			});

		} catch (Exception e) {
			log.warning("No question selected " + e.getMessage() + "--" + e.getCause());
		}

	}
		

	@FXML
	void addNotizien(MouseEvent event) {

		FXMLLoader fxmlLoader = new FXMLLoader();

		try {

			fxmlLoader.setLocation(getClass().getResource("/GUI/Notizien.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			NotizienController nController = fxmlLoader.getController();

			noteStage.setTitle("Übersicht - Alle gestellte fragen ");
			noteStage.setScene(scene);
			noteStage.centerOnScreen();
			noteStage.setResizable(false);
			noteStage.show();

			try {

				int idFrage = get_selected_question().getID();
				nController.frage.setID(idFrage);

			} catch (Exception e) {
				log.warning("Can't add notes: No question selected...");
			}

		} catch (IOException e) {
			log.warning("Cant load View : Notizien.fxml"); // +e.getMessage() +e.getCause()
			e.printStackTrace();
		}

	}

	
	@FXML // Ask a question
	void frageStellen(MouseEvent event) throws SQLException {

		Frage frage = get_selected_question();
		dbQuery.frageStellen(frage, true);
		showQuestions();

	}

	
	@FXML // Un-Ask a question. (If ask button was clicked by mistake)
	void nichtStellen(MouseEvent event) throws SQLException {

		Frage frage = get_selected_question();
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
	
	
	public int noteBerechnen() {
		int note = 0;
		double maxPunkte=0;
		UebersichtController ue = new UebersichtController();
		ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();
		gestellteFragen.add(ue.get_selected_question());
		for(Frage frage : gestellteFragen) {
			maxPunkte = maxPunkte + frage.getPunkte();
		}
		float erreichtePunkte = ue.gesPunktzahl;
		
		float floatNote = erreichtePunkte/(float)maxPunkte;
		
		if(floatNote>=0.95) {
			note = 100;
		}else if(floatNote<0.95 && floatNote>=0.90) {
			note = 130;
		}else if(floatNote<0.90 && floatNote>=0.85) {
			note = 170;
		}else if(floatNote<0.85 && floatNote>=0.80) {
			note = 200;
		}else if(floatNote<0.80 && floatNote>=0.75) {
			note = 230;
		}else if(floatNote<0.75 && floatNote>=0.70) {
			note = 270;
		}else if(floatNote<0.70 && floatNote>=0.65) {
			note = 300;
		}else if(floatNote<0.65 && floatNote>=0.60) {
			note = 330;
		}else if(floatNote<0.60 && floatNote>=0.55) {
			note = 370;
		}else if(floatNote<0.55 && floatNote>=0.50) {
			note = 400;
		}else {
			note = 500;
		}
		
		
		
		
		
		
		return note;
	}
	

	@FXML
	public void pdfErstellenClick(MouseEvent event) throws FileNotFoundException, DocumentException, SQLException {
		String katalogName = katalogeComboBox.getValue();
		ObservableList<Frage> fragen = FXCollections.observableArrayList();
		
		// Load DBQueries Result set with all asked questions
		dbQuery.fragenLaden_gestellt(katalogName);

		// Fill list with questions in result set
		fillList(fragen);

		FileChooser fc = new FileChooser();
		//Window stage = pdfErstellen.getScene().getWindow();

		fc.setTitle("Save to PDF");
		fc.setInitialFileName("file name.pdf");

		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));

		File file = fc.showSaveDialog(Main.mainWindow);

		if (file != null) {

			String path = file.getAbsolutePath();

			try {
				
				Document document = new Document();
				FileOutputStream fos = new FileOutputStream(path);
				PdfWriter.getInstance(document, fos);
				
				
				document.open();
				
				PDFCreate.addMetaData(document);
				PDFCreate.addTitlePage(document, fragen, studCon.selectedStudent, LoginController.globPruef);
				PDFCreate.add_questions(document, fragen);

				document.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		
		//Set every 'asked' property to false after exam is done 
		dbQuery.setAllFalse();
	}
	
	/*
	 * public Image add_notes() { FileChooser choser = new FileChooser();
	 * 
	 * try {
	 * 
	 * //Create an image object //return image; } catch (Exception e) {
	 * e.printStackTrace(); return null; } }
	 */
	
	public void protokollieren() throws SQLException {
		String katalogName = katalogeComboBox.getValue();

		ObservableList<Frage> fragen = FXCollections.observableArrayList();
		// Load DBQueries Result set with all asked questions
		// log.info("kat name is: " +katalogName);
		dbQuery.fragenLaden_gestellt(katalogName);

		// Fill list with questions in result set
		fillList(fragen);
		// log.info(fragen.toString());

		FileChooser fc = new FileChooser();
		Window stage = Main.mainWindow;

		fc.setTitle("Save to PDF");
		fc.setInitialFileName("file name.pdf");

		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));

		File file = fc.showSaveDialog(stage);

		if (file != null) {

			String path = file.getAbsolutePath();

			try {

				
				Document pdfDocument = new Document();
				FileOutputStream fos = new FileOutputStream(path);
				PdfWriter.getInstance(pdfDocument, fos);
				pdfDocument.open();
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("");
				alert.setHeaderText(null);
				alert.setContentText("Möchten Sie Notizen hinfügen?");

				PDFCreate.addMetaData(pdfDocument);
				PDFCreate.addTitlePage(pdfDocument, fragen, studCon.globStud, logCon.globPruef);
				PDFCreate.add_questions(pdfDocument, fragen);

				// Ok -> Yes
				Optional<ButtonType> ok = alert.showAndWait();

				if (ok.get() == ButtonType.OK) {

					//Create a new note obj -> it's chooser window is instantiated and shown
					Note note = new Note(Main.mainWindow);
					//Add an image to the pdf document
					PDFCreate.add_image(pdfDocument, note.getImg());
					

				}
				
				
				 
				 
				

				pdfDocument.close();
				System.out.println(logCon.globPruef.getNachname()+logCon.globPruef.getVorname());
				fos.close();
				//Reset fields for gestellt and notes after exam is over and protocolled
				dbQuery.reset();
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
			try {	
				StartController.setWindow("Statistik");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@FXML // Shows the overview if it isn't already showing, brings it to the front if the
			// button is clicked after it's showing
	void uebersichtAnzeigen(MouseEvent event) throws IOException, SQLException {

		if (!uebersichtIsShowing()) {
			showUebersicht();
		} else if (!UebersichtController.stage.isFocused()) {
			UebersichtController.stage.toFront();
		}

	}



	/////////////// Imp. func. Testing ///////////////////

	// The test button and method are used for debugging and testing features

	@FXML
	private Button test;

	@FXML
	void test(MouseEvent event) throws SQLException {
		katalogName = katalogeComboBox.getValue();
		dbQuery.fragenLaden_gestellt(katalogName);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		// Make sure the table doesn't lose the selected question
		frageTabelle.getSelectionModel().selectedIndexProperty().addListener(e -> {
			int selectedRow = frageTabelle.getSelectionModel().getSelectedIndex();
			frageTabelle.requestFocus();
			frageTabelle.getSelectionModel().select(selectedRow);
			frageTabelle.getFocusModel().focus(selectedRow);
		});
				
		
		
		// Handle achieved points by switching the toggle for grundlageNiveau on/off
		grundlage_switch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> selected, Boolean oldValue, Boolean newValue) {

				Frage frage = get_selected_question();

				if (oldValue == false && newValue == true) {
					frage.setErreichtePunkte((float) (frage.getPunkte() * 0.15));
					/*
					 * gut_switch.selectedProperty().set(false);
					 * sehrgut_switch.selectedProperty().set(false);
					 */
				} else if (newValue == false){
					frage.setErreichtePunkte(0);
					System.out.println("set to 0 from method: grundlage");
				}
				try {
					dbQuery.erreichte_punkte_speichern(frage, frage.getErreichtePunkte());
				} catch (SQLException e) {
					log.warning("Achieved points can not be saved into the database! " + e.getMessage());
				}
				erreichte_punkte.setText(String.valueOf(frage.getErreichtePunkte()));

			}
		});

		// Handle achieved points by switching the toggle for gut on/off
		gut_switch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> selected, Boolean oldValue, Boolean newValue) {
				Frage frage = get_selected_question();

				if (newValue == true) {
					frage.setErreichtePunkte((float) (frage.getPunkte() * 0.45));
				} else if (newValue == false){
					frage.setErreichtePunkte(0);
					System.out.println("set to 0 from method: gut");
				}
				try {
					dbQuery.erreichte_punkte_speichern(frage, frage.getErreichtePunkte());
				} catch (SQLException e) {
					log.warning("Achieved points can not be saved into the database!" + e.getMessage() + e.getCause());
				}
				erreichte_punkte.setText(String.valueOf(frage.getErreichtePunkte()));

			}
		});

		// Handle achieved points by switching the toggle for sehrGut on/off
		sehrgut_switch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> selected, Boolean oldValue, Boolean newValue) {
				Frage frage = get_selected_question();

				if (newValue == true) {
					frage.setErreichtePunkte((float) (frage.getPunkte()));
				} else if (newValue == false){
					frage.setErreichtePunkte(0);
					System.out.println("set to 0 from method: sehrgut");
				}

				try {
					dbQuery.erreichte_punkte_speichern(frage, frage.getErreichtePunkte());
				} catch (SQLException e) {
					log.warning("Achieved points can not be saved into the database!" + e.getMessage() + e.getCause());
				}
				erreichte_punkte.setText(String.valueOf(frage.getErreichtePunkte()));

			}
		});

		
		
		// Navigate frageTabelle with the arrow keys and ENTER button
		frageTabelle.setOnKeyPressed((KeyEvent ke) -> {

			Frage selected = new Frage();
			switch (ke.getCode())

			{
			case DOWN:

				selected = frageTabelle.getSelectionModel().getSelectedItem();
				ask_switch.setSelected(selected.isGestelltbool());
				showDetails(selected);

				ke.consume();
				break;

			case UP:
				selected = frageTabelle.getSelectionModel().getSelectedItem();
				ask_switch.setSelected(selected.isGestelltbool());
				showDetails(selected);
				ke.consume();
				break;
			case ENTER:
				selected = frageTabelle.getSelectionModel().getSelectedItem();
				Boolean asked = selected.isGestelltbool();
				ask_switch.setSelected(asked);
				if (asked) {
					try {
						unask(selected);
						ask_switch.setSelected(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						ask(selected);
						ask_switch.setSelected(true);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				showDetails(selected);
				ke.consume();
				break;
			default:
				break;
			}
		});

		// Side menu
		try {
			VBox box = FXMLLoader.load(getClass().getResource("/GUI/DrawerContent.fxml"));

			drawer.setSidePane(box);
			drawer.setOverLayVisible(false);
			for (Node node : box.getChildren()) {
				if (node.getAccessibleText() != null) {
					node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
						switch (node.getAccessibleText()) {

						// GUI Navigation - Go to Pruefung starten screen
						case "FragekatalogErstellen":
							try {
								StartController.setWindow("Katalogverwaltung");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;

						// GUI Navigation - Go to StatistikAnsehen screen (SOON)
						case "StatistikAnsehen":
							try {
								StartController.setWindow("Statistik");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case "Protokollieren":
							try {
								protokollieren();
								writeExcel(noteBerechnen());
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						case "StudentenImportieren":
							try {
								studenten_importieren();
							} catch (IOException | SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						}

					});

				}

			}
			
			
			HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
			transition.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
				transition.setRate(transition.getRate() * -1);
				transition.play();

				if (drawer.isHidden()) {
					drawer.close();
				} else {
					drawer.open();
				}

			});
		} catch (IOException e1) {
			Logger.getLogger(PruefungController.class.getName()).log(Level.SEVERE, null, e1);
		}

	}
	

    @FXML
    private Button reset;
    
    @FXML
    void reset(MouseEvent event) throws SQLException {
    	dbQuery.reset();
    }
    
}