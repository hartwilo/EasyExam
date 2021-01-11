package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
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
	public Button frageBearbeiten;

	@FXML
	public Button katalogSpeichern;

	@FXML
	public Button katalogAnlegen;

	@FXML
	private Button refresh;
	
    @FXML
    private MenuItem PrüfungStarten;

    @FXML
    private MenuItem StatistikAnsehen;

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
	
	private boolean auswahl;
	
	static DBQueries dbQuery = new DBQueries(DBConn.connection);
	public static String katalogName;
	
	
	
//////////////////Java Methods //////////////////////

	/**
	 * method to initialize the window
	 * 
	 * @param location URL
	 * @param resources ResourceBundle 
	 */
public void initialize(URL location, ResourceBundle resources) {
		
//		frageTabelle.getStylesheets().add(this.getClass().getResource("../../../../css/@fragetabelle.css").toExternalForm());
//		fragetabelle.getStylesheets().add("../../../../css/@fragetabelle.css");
		
		
		
		fragetabelle.setOnKeyPressed((KeyEvent ke) ->
        {
        	
        	Frage selected = new Frage();
            switch (ke.getCode())
      
            {
                case DOWN:
                	
                	selected = fragetabelle.getSelectionModel().getSelectedItem();
                	auswahl = selected.isGestelltbool();
                	
                    ke.consume();
                    break;
               
                case UP:
                	selected = fragetabelle.getSelectionModel().getSelectedItem();
                	auswahl = selected.isGestelltbool();
                    ke.consume();
                    break;
                case ENTER:
                	selected = fragetabelle.getSelectionModel().getSelectedItem();
                	auswahl = selected.isGestelltbool();
                	 if (auswahl) {
             			try {
             				selected.setGestelltbool(false);
             			} catch (Exception e) {
             				e.printStackTrace();
             			}
             		} else {
             			try {
             				selected.setGestelltbool(true);
             			} catch (Exception e) {
             				e.printStackTrace();
             			}

             		}
                    ke.consume();
                    break;
                default:
                    break;
            }
        });
		}

		/**
		 * The method initializes the selected question 
		 * 
		 * @return selected Question 
		 */
		public Frage get_selected_question() {
		
			try {
				Boolean gestellt = fragetabelle.getSelectionModel().getSelectedItem().isGestelltbool();
				int id = fragetabelle.getSelectionModel().getSelectedItem().getID();
				int niv = fragetabelle.getSelectionModel().getSelectedItem().getNiveau();
				float punkte = (float) fragetabelle.getSelectionModel().getSelectedItem().getPunkte();
				String stellung = fragetabelle.getSelectionModel().getSelectedItem().getFrageStellung();
				String loesung = fragetabelle.getSelectionModel().getSelectedItem().getMusterloesung();
				String fragekatalog = fragetabelle.getSelectionModel().getSelectedItem().getFragekatalog();
				String modul = fragetabelle.getSelectionModel().getSelectedItem().getModul();
				String grundlage = fragetabelle.getSelectionModel().getSelectedItem().getGrundLageNiveau();
				String gut = fragetabelle.getSelectionModel().getSelectedItem().getGut();
				String sehrGut = fragetabelle.getSelectionModel().getSelectedItem().getSehrGut();
				String themengebiet = fragetabelle.getSelectionModel().getSelectedItem().getThemengebiet();
				
		
				return new Frage(id, stellung, loesung, niv, themengebiet, fragekatalog, punkte, gestellt, modul, grundlage,
						gut, sehrGut);
		
			} catch (Exception e) {
				log.warning("No question from table selected, details cant be read!");
				return null;
			}
		
		}
	

	/**
	 * This method loads relevant question data into a ViewTable in the GUI
	 * 
	 * @throws SQLException
	 */
	public void fragenAnzeigen() throws SQLException {

		ObservableList<Frage> frageListe = FXCollections.observableArrayList();

		// Load DBQueries Result Set with questions from DB
		katalogName = katalogComboBox.getValue();
		DBQueries.rs = dbQuery.alleFrageLaden(katalogName);

		fillList(frageListe);
		showInMainTable(frageListe);

	}

	/**
	 * This method adds questions to a list 
	 * 
	 * @param fragen ObservableList with Frage objects 
	 * @throws SQLException
	 */
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
	
	/**
	 * Define structure of FXML Table Cells you want to display data with
	 * 
	 * @param fragen ObservableList with Frage objects 
	 */
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
		
	/**
	 * The method shows a warning with a individual text
	 * 
	 * @param warnung String with text for warning message 
	 */
	private void warnungAnzeigen(String warnung) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText(null);
			alert.setContentText(warnung);
			alert.showAndWait();
	}


//////////////// FXML methods ////////////////

	/**
	 * This method loads relevant question data into a ViewTable in the GUI (as soon
 	 * as the mouse is entered into the GUI)
	 * 
	 * @param event button is clicked
	 * @throws SQLException
	 */
	@FXML 
	public void fragenLaden(MouseEvent event) throws SQLException {
		fragenAnzeigen();

	}

	/**
	 * This method deletes questions from a currently Selected question catalog
	 * 
	 * @param event button is clicked
	 * @throws SQLException
	 */
	@FXML 
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

	/**
	 * The following method is used to fill the Catalog ComboBox with all existing
 	 * values in the database.
	 * 
	 * @param event 
	 * @throws SQLException
	 */
	@FXML
	private void katalogeLaden(MouseEvent event) throws SQLException {
		katalogComboBox.setItems(dbQuery.katalogeAuslesen());
		fragenAnzeigen(); 
	}

	/**
	 * Delete the selected catalog of questions
	 * 
	 * @param event button is clicked
	 * @throws SQLException
	 */
	@FXML
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

	/**
	 * Create a catalog with questions 
	 * 
	 * @param event button is clicked 
	 * @throws IOException
	 */
	@FXML
	void katalogAnlegen(MouseEvent event) throws IOException {
		
		StartController.setWindow("Katalogverwaltung");
	}

	/**
	 * Add a question to the selected catalog. 
	 * If none is selected a new catalog name must be provided in the relevant TextArea.
	 * 
	 * @param event button is clicked 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML 
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
	
	/**
	 * The method is used to edit an existing question 
	 * It's not working and in implementation 
	 * 
	 * @param event button is clicked 
	 * @throws IOException
	 * @throws SQLException
	 */
	@FXML
		void frageBearbeiten(MouseEvent event) throws IOException, SQLException {
		
		if(katalogNameTextField.getText().isEmpty() && katalogComboBox.getValue()==null) {
			warnungAnzeigen("Bitte Fragekatalog auswählen!");
			System.out.println("zweig1");
		}
		/*else if (katalogComboBox.getValue()==null) {
			katalogName = katalogNameTextField.getText();
			System.out.println("zweig2");
			log.info("Adding question to: "+katalogName);
			StartController.setWindow("Frageverwaltung");
			if(auswahl==true) {
				warnungAnzeigen("Bitte Frage auswählen");
			
		}*/
		else if (katalogNameTextField.getText().isEmpty()) {
			if(auswahl==true) {
				warnungAnzeigen("Bitte Frage auswählen");
			}
			else {
				katalogName = katalogComboBox.getValue();
				System.out.println("zweig3");
				log.info("Update Question from: "+katalogName);
				Frage frageUpdate = new Frage();
				FragebearbeitungController.setFrage(frageUpdate);
				StartController.setWindow("Fragebearbeitung");
			}
		}
		
	}

	/**
	 * GUI Navigation - Save all current changes and go back to the start screen
	 * 
	 * @param event button is clicked 
	 * @throws IOException
	 */
	@FXML
	void katalogSpeichern(MouseEvent event) throws IOException {

		StartController.setWindow("Startscreen");
	}

	/**
	 * GUI Navigation - Go to Pruefung starten screen
	 * 
	 * @param event ActionEvent navigation is clicked 
	 */
	@FXML 
	void PrüfungStartenClick(ActionEvent event) {
		try {
			StartController.setWindow("Pruefung2");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * GUI Navigation - Go to StatistikAnsehen screen 
	 * 
	 * @param event ActionEvent navigation is clicked 
	 */
	@FXML 
	void StatistikAnsehenClick(ActionEvent event) {
		try {
			StartController.setWindow("Statistik");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * The method saves the name of the catalog with questions into a variable 
	 * 
	 * @param event ActionEvent 
	 */
	@FXML
	void katalogNameLesen(ActionEvent event) {
		katalogName = katalogNameTextField.getText();
	}	
		

}
