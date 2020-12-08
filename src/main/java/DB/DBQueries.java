package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

import de.hftstuttgart.EasyExam.Models.Frage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DBQueries {

	private static final Logger log;
	Connection connection = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	// Must be closed
	public static ResultSet rs;

	/**
	 * SQL-Query to save questions in Database
	 * 
	 * @param fragestellung
	 * @param musterloesung
	 * @param niveau
	 * @param punkte
	 * @param gestellt
	 * @param themengebiet
	 * @param fragekatalog
	 * @param modul
	 * @throws SQLException
	 */

	// Save/Create a new question in the db
	public int frageSpeichern(Frage frage) throws SQLException {
		DBConn.connection.setAutoCommit(true);

		String query = "INSERT INTO FRAGE(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stmt = DBConn.connection.prepareStatement(query);

		String fragestellung = frage.getFrageStellung();
		String musterloesung = frage.getMusterloesung();
		String themengebiet = frage.getThemengebiet();
		String fragekatalog = frage.getFragekatalog();
		String modul = "tbd"; // TODO!
		String grundlage = frage.getGrundLageNiveau();
		String gut = frage.getGut();
		String sehrGut = frage.getSehrGut();
		int niveau = frage.getNiveau();
		double punkte = frage.getPunkte();
		Boolean gestellt = false;

		stmt.setString(1, fragestellung);
		stmt.setString(2, musterloesung);
		stmt.setInt(3, niveau);
		stmt.setDouble(4, punkte);
		stmt.setBoolean(5, gestellt);
		stmt.setString(6, themengebiet);
		stmt.setString(7, fragekatalog);
		stmt.setString(8, modul);
		stmt.setString(9, grundlage);
		stmt.setString(10, gut);
		stmt.setString(11, sehrGut);

		return stmt.executeUpdate();
	}

	// Load all questions
	public ResultSet alleFrageLaden(String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "SELECT * FROM Frage where Fragekatalog = " + "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}

	// Load questions based on topic
	public ResultSet frageLaden_themengebiet(String themengebiet, String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "Select * from Frage where themengebiet =" + "'" + themengebiet + "'" + " and Fragekatalog = "
				+ "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);
	}

	// Load questions based on diff level
	public ResultSet frageLaden_niveau(int niveau, String katalog) throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "Select * from Frage where niveau =" + "'" + niv + "'" + " and Fragekatalog = " + "'" + katalog
				+ "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}

	// Load questions based on diff. level and topic
	public ResultSet frageLaden_niveau_themengebiet(int niveau, String themengebiet, String katalog)
			throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "Select * from Frage where niveau = " + "'" + niv + "'" + " and themengebiet = " + "'"
				+ themengebiet + "'" + " and Fragekatalog = " + "'" + katalog + "'";

		log.info("Last query: " + query);
		return DBQueries.rs = stmt.executeQuery(query);
	}

	// Load all asked questions
	public ResultSet fragenLaden_gestellt(String katalog) throws SQLException {

		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "SELECT * from Frage WHERE gestellt = 1 " + "AND Fragekatalog = " + "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}

	// Ask a question to a student - change boolean property of the question
	public int frageStellen(Frage frage, Boolean gestellt) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		int intGestellt = gestellt ? 1 : 0;
		int id = frage.getID();
		String query = "UPDATE Frage SET gestellt = " + intGestellt + " where idFrage = " + id;

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}

	// Delete a single question
	public void frageLoeschen(int ID) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "DELETE FROM Frage WHERE idFrage = " + ID;

		log.info("Last query: " + query);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("Möchten Sie die Frage wirklich löschen?");
	// Only delete if ok is clicked
		Optional<ButtonType> ok = alert.showAndWait();

		if (ok.get() == ButtonType.OK) {

			int i = stmt.executeUpdate(query);
			if (i == 1)
				log.info("Question: " + ID + "succesfully deleted");
		}
	}

	// Delete an entire catalog of questions
	public void katalogLoeschen(String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText(null);
		alert.setContentText("Der Katalog " + katalog + " wird gelöscht");
	// Only delete if ok is clicked
		Optional<ButtonType> ok = alert.showAndWait();

		if (ok.get() == ButtonType.OK) {

			String query = "DELETE FROM Frage WHERE Fragekatalog = " + "'" + katalog + "'";
			log.info("Last query: " + query);
			int i = stmt.executeUpdate(query);

			if (i == 1)
				log.info(katalog + " succesfully deleted");

		}
	}

	/**
	 * SQL-Query to get subject areas from Database
	 * 
	 * @return
	 * @throws SQLException
	 * 
	 */
	public ObservableList<String> themengebieteAuslesen(String katalog) throws SQLException {
		ObservableList<String> themengebiete = FXCollections.observableArrayList();
		Statement stmt = DBConn.connection.createStatement();

		String query = "SELECT themengebiet FROM Frage where Fragekatalog = " + "'" + katalog + "'";

		// log.info("Last query: "+query);
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			String s = rs.getString("themengebiet");
			if (!themengebiete.contains(s)) {
				themengebiete.add(s);
			}
		}
		return themengebiete;
	}

	public ObservableList<String> katalogeAuslesen() throws SQLException {
		ObservableList<String> kataloge = FXCollections.observableArrayList();
		Statement stmt = DBConn.connection.createStatement();

		String query = "SELECT Fragekatalog FROM Frage";

		// log.info("Last query: "+query);
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			String s = rs.getString("Fragekatalog");
			if (!kataloge.contains(s)) {
				kataloge.add(s);
			}
		}
		return kataloge;
	}

	// Old method for saving questions, no longer used
	public int frageSpeichern_SIDB(String fragestellung, String musterloesung, int niveau, double punkte,
			String gestellt, String themengebiet, String fragekatalog, String modul) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "INSERT INTO Frage(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul) "
				+ "Values('" + fragestellung + "','" + musterloesung + "', '" + niveau + "', '" + punkte + "', '"
				+ gestellt + "', '" + themengebiet + "', '" + fragekatalog + "', '" + modul + "')";

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}

	public int setAllFalse() throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		String query = "UPDATE Frage SET gestellt = 0";

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}

}
