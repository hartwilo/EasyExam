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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UebersichtController {

	private static final Logger log;

	DBQueries dbQuery = new DBQueries(DBConn.connection);

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

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

	public void show() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/GUI/Uebersicht.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = new Stage();
		stage.setTitle("Übersicht - Alle gestellte fragen ");
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setResizable(false);
		stage.show();
	}

	

	/*
	 * public void fillAskedList(ObservableList<Frage> fragen) throws SQLException {
	 * 
	 * // String katalog = PruefungController.katalogName; //
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * // Create Frage.objs from result set and add to list while
	 * (DBQueries.rs.next()) { // Prep variables for Frage constructor int ID =
	 * DBQueries.rs.getInt("idFrage"); String thema =
	 * DBQueries.rs.getString("Themengebiet"); String fragestellung =
	 * DBQueries.rs.getString("Fragestellung"); String musterloesung =
	 * DBQueries.rs.getString("Musterloesung"); int niveau =
	 * DBQueries.rs.getInt("Niveau"); Float punkte =
	 * DBQueries.rs.getFloat("Punkte"); Boolean istGestellt =
	 * DBQueries.rs.getBoolean("gestellt"); String modul =
	 * DBQueries.rs.getString("Modul"); String fragekatalog =
	 * DBQueries.rs.getString("Fragekatalog");
	 * 
	 * String grundlage = DBQueries.rs.getString("grundlagenniveau"); String gut =
	 * DBQueries.rs.getString("gut"); String sehrGut =
	 * DBQueries.rs.getString("SehrGut");
	 * 
	 * if (istGestellt == true) fragen.add(new Frage(ID, fragestellung,
	 * musterloesung, niveau, thema, fragekatalog, punkte, istGestellt, modul,
	 * grundlage, gut, sehrGut));
	 * 
	 * } }
	 */
	
	
	public void displayUebersicht(ObservableList<Frage> fragen) {
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
	
	

}
