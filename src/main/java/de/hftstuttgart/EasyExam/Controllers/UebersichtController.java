package de.hftstuttgart.EasyExam.Controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import DB.DBConn;
import DB.DBQueries;
import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UebersichtController {

	private static final Logger log;

	DBQueries dbQuery = new DBQueries(DBConn.connection);

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	public static Stage stage = new Stage();
	private StringProperty niv = new SimpleStringProperty();


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

	// Labels
    @FXML
    private Label aktuellerNiveau;
    
    @FXML
    private Label level;

	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/Uebersicht.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		// Stage stage = new Stage();
		stage.setTitle("Übersicht - Alle gestellte fragen ");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();

		// A possible substitute for "on mouse entered" FXML method
		/*
		 * stage.setOnShowing(new EventHandler<WindowEvent>() {
		 * 
		 * @Override public void handle(WindowEvent event) { try { uebersicht();
		 * log.info("breh"); } catch (SQLException e) { e.printStackTrace(); } } });
		 */

	}

	/////////// Java Methods ////////////////////

	// Show all asked questions in the table
	public void uebersicht() throws SQLException {
		ObservableList<Frage> gestellteFragen = FXCollections.observableArrayList();

		String katalogName = PruefungController.katalogName;
		log.info("Catalog name static var is: " + katalogName);
		dbQuery.fragenLaden_gestellt(katalogName);

		fillList(gestellteFragen);

		//log.info(gestellteFragen.get(0).getFrageStellung());

		showInUebersichtTable(gestellteFragen); 
		niveauBerechnen();
		
		
	}

	// Fill an observable list with questions from the DBQueries Result Set
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

	// Set up table and columns and start displaying list
	public void showInUebersichtTable(ObservableList<Frage> fragen) {
		fxcolumn_fragestellung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getFrageStellung()));
		fxcolumn_punkte.setCellValueFactory(features -> new ReadOnlyDoubleWrapper(features.getValue().getPunkte()));
		fxcolumn_thema
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getThemengebiet()));
		fxcolumn_niveau.setCellValueFactory(features -> new ReadOnlyIntegerWrapper(features.getValue().getNiveau()));
		fxcolumn_musterloesung
				.setCellValueFactory(features -> new ReadOnlyStringWrapper(features.getValue().getMusterloesung()));
		fragetabelle.setFixedCellSize(25);
		fragetabelle.setItems(fragen);
	}

	public void niveauBerechnen() throws SQLException {
		aktuellerNiveau.textProperty().bind(niv);

		String katalogName = PruefungController.katalogName;
		log.info("Catalog name static var is: " + katalogName);
		dbQuery.fragenLaden_gestellt(katalogName);
		 

		double niveau = 0;
		int askedQuestions = fragetabelle.getItems().size();

		while (DBQueries.rs.next()) {
			int i = DBQueries.rs.getInt("Niveau");
			// log.info("XD"+frageNiveau);
			niveau = niveau + i;

		}
		double result  = niveau / askedQuestions;
		String sResult = Double.toString(niveau / askedQuestions);
		
		log.info("Niv =  " + result);	
		
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

	///////////////// FXML Methods ////////////////////

	@FXML
	void showQuestions(MouseEvent event) throws SQLException {
		uebersicht();

	}

	/////////////// Testing Methods ////////////////////
	@FXML
	private Button test;

	@FXML
	void test(MouseEvent event) throws SQLException {
		niveauBerechnen();
	}

}
