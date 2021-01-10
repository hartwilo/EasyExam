package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXTextField;
import com.sun.glass.events.KeyEvent;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class UebersichtController implements Initializable {

	private static final Logger log;

	DBQueries dbQuery = new DBQueries(DBConn.connection);

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	public static Stage stage = new Stage();
	private StringProperty niv = new SimpleStringProperty();
	private StringProperty erp = new SimpleStringProperty(); //erreichte punkte
	


	// Table and its columns
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
	private TableColumn<Frage, Number> fxcolumn_erreichte_punkte;
	
	// TextFields

	@FXML
	private JFXTextField erreichte_punkte;

	 // Labels    
    @FXML
    private Label aktuellerNiveau;
    
    @FXML
    private Label level;
    
    /**
	 * Create a frage.obj from the selected question in the View Table
	 * 
	 * @return
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
			Float erreichtePunkte = fragetabelle.getSelectionModel().getSelectedItem().getErreichtePunkte();

			return new Frage(id, stellung, loesung, niv, themengebiet, fragekatalog, punkte, gestellt, modul, grundlage,
					gut, sehrGut, erreichtePunkte);

		} catch (Exception e) {
			log.warning("No question from table selected, details cant be read!");
			return null;
		}
    }
    
    /**
     * The method opens the overview screen
     * 
     * @throws IOException
     */
	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/Uebersicht.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("Übersicht - Alle gestellte fragen ");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();


	}

	/////////// Java Methods ////////////////////

	
	/**
	 * Show all asked questions in the table
	 * 
	 * @throws SQLException
	 */
	public void uebersicht() throws SQLException {
		ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();

		String katalogName = PruefungController.katalogName;
		//log.info("Catalog name static var is: " + katalogName);
		dbQuery.fragenLaden_gestellt(katalogName);

		fillList(gestellteFragen);

		//log.info(gestellteFragen.get(0).getFrageStellung());

		showInUebersichtTable(gestellteFragen); 
		niveauBerechnen();
		
		
	}

	
	/**
	 * Fill an observable list with questions from the DBQueries Result Set
	 * 
	 * @param fragen ObservableList with Frage objects 
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
			Boolean istGestellt = DBQueries.rs.getBoolean("gestellt");
			String modul = DBQueries.rs.getString("Modul");
			String fragekatalog = DBQueries.rs.getString("Fragekatalog");

			String grundlage = DBQueries.rs.getString("grundLageNiveau");
			String gut = DBQueries.rs.getString("gut");
			String sehrGut = DBQueries.rs.getString("SehrGut");
			Float erreichte_punkte = DBQueries.rs.getFloat("Punkte_erreicht");
			
			fragen.add(new Frage(ID, fragestellung, musterloesung, niveau, thema, fragekatalog, punkte, istGestellt,
					modul, grundlage, gut, sehrGut, erreichte_punkte));
		}
	}

	
	/**
	 * Set up table and columns and start displaying list
	 * 
	 * @param fragen ObservableList with Frage objects 
	 */
	public void showInUebersichtTable(ObservableList<Frage> fragen) {
		
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
		
		fxcolumn_erreichte_punkte
				.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getErreichtePunkte()));
		
		fxcolumn_erreichte_punkte.setEditable(true);
		fragetabelle.setFixedCellSize(25);
		fragetabelle.setItems(fragen);
	}
	

	/**
	 * The method is used to determinate the level
	 * 
	 * @throws SQLException
	 */
	public void niveauBerechnen() throws SQLException {
		//aktuellerNiveau.textProperty().bind(niv);
		String katalogName = PruefungController.katalogName;
		log.info("Catalog name static var is: " + katalogName);
		dbQuery.fragenLaden_gestellt(katalogName);
		 

		double niveau = 0;
		int askedQuestions = fragetabelle.getItems().size();

		while (DBQueries.rs.next()) {
			int i = DBQueries.rs.getInt("Niveau");
			// log.info("Test :"+ frageNiveau);
			niveau = niveau + i;

		}
		double result  = niveau / askedQuestions;
		String sResult = Double.toString(niveau / askedQuestions);
		
		//log.info("Niv =  " + result);	
		
		if (result >= 1 && result < 2) {
			level.setText("Schlecht");
			level.setTextFill(Color.web("#ff0000", 0.8));
		} else if (result >= 2 && result <= 2.5) {
			level.setText("Gut");
			level.setTextFill(Color.web("#eec448", 0.8));
		} else {
			level.setText("Sehr gut");
			level.setTextFill(Color.web("#26e400", 0.8));
		}
		
		niv.set(sResult);
		

	}
	
	/**
	 * The method sets the points to the question
	 * 
	 */
	public void set_erreichte_punkte() {
		
		try {
			Frage frage = get_selected_question();
			dbQuery.erreichte_punkte_speichern(frage, Double.valueOf(erp.getValue()));
			uebersicht();
			
		} catch (Exception e ) {
			log.warning("Accuired points could not be saved" 
					+e.getMessage() +" " +e.getCause());
		}
		
	}
	
	
	///////////////// FXML Methods ////////////////////
	
	
	/**
	 * The method shows all questions 
	 * 
	 * @param event
	 * @throws SQLException
	 */
	@FXML
	void showQuestions(MouseEvent event) throws SQLException {
		uebersicht();

	}

	/////////////// Testing Methods ////////////////////
	
	@FXML
	private Button test;

	/**
	 * The method tests the method niveauBerechnen
	 * 
	 * @param event
	 * @throws SQLException
	 */
	@FXML
	void test(MouseEvent event) throws SQLException {
		niveauBerechnen();
	}

	/**
	 * initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	
		erreichte_punkte.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                	erp.set(newValue);
                	erreichte_punkte.setText(newValue);
                	System.out.println(erp + " <- err.punkte");
                } else {
                	System.out.println("No number key pressed!");
                	erp.set(""); 
                	erreichte_punkte.setText("");
                }
            }
        });
		
		erreichte_punkte.setOnKeyPressed(event -> {
			   if(event.getCode() == KeyCode.ENTER){
			    set_erreichte_punkte();
			    erreichte_punkte.setText("");
			   }
			}); 
		
		
		
	}

}
