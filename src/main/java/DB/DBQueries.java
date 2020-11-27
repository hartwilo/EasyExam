package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DBQueries {
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	

	//Must be closed
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
	
	
	
	
	public int frageSpeichern(String fragestellung, String musterloesung, int niveau, String punkte, String gestellt, String themengebiet, String fragekatalog, String modul) throws SQLException
	{
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "INSERT INTO Frage(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul) "
				+ "Values('" 
				+ fragestellung + "','"
				+ musterloesung + "', '" 
				+ niveau +"', '" 
				+ punkte + "', '" 
				+ gestellt + "', '" 
				+ themengebiet + "', '"
				+ fragekatalog + "', '" 
				+ modul +"')";
		
		log.info("Last query: "+query);
		return stmt.executeUpdate(query);
	}
	
	
	///Changes 25.11 -Gjergji TODO add logic for Katalog Name to query
	public ResultSet alleFrageLaden(String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "SELECT * FROM Frage where Fragekatalog = "+ "'" + katalog + "'" ;
		
		log.info("Last query: "+query);
		return DBQueries.rs = stmt.executeQuery(query);
		
	}
	
	//Changes 26.11 - Gjergji Select questions based on topic
	public ResultSet frageLaden_themengebiet(String themengebiet, String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "Select * from Frage where themengebiet =" + "'" + themengebiet + "'"
		+ " and Fragekatalog = "+"'" + katalog + "'";
		
		log.info("Last query: "+query);
		return DBQueries.rs = stmt.executeQuery(query);
	}
	
	//Changes 26.11 - Gjergji Select questions based on diff. level
	public ResultSet frageLaden_niveau(int niveau, String katalog) throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "Select * from Frage where niveau =" + "'" + niv + "'"
		+ " and Fragekatalog = "+"'" + katalog + "'";
		
		log.info("Last query: "+query);
		return DBQueries.rs = stmt.executeQuery(query);
		
	}
	
	////Changes 26.11 - Gjergji Select questions based on diff. level and topic
	public ResultSet frageLaden_niveau_themengebiet(int niveau, String themengebiet, String katalog) throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "Select * from Frage where niveau = " + "'" + niv + "'" 
		+ " and themengebiet = " + "'" + themengebiet + "'"			
		+ " and Fragekatalog = "+"'" + katalog + "'";
		
		log.info("Last query: "+query);
		return DBQueries.rs = stmt.executeQuery(query);
	}
	
	
	//Changes 25.11 -Gjergji
	public void frageLoeschen(int ID) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "DELETE FROM Frage WHERE idFrage = " + ID;
		
		log.info("Last query: "+query);
		int i = stmt.executeUpdate(query);	
		if (i == 1) log.info("Question: " +ID+ "succesfully deleted");
	}
	
	// TODO Resolve possible CONFLICT: 2 Catalogs with same name!
	public void katalogLoeschen(String katalog) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();

		// TODO: Make the stmt only execute after confirming dialog - No delete if no
		// confirm
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
	public ObservableList<String> themengebieteAuslesen(String katalog) throws SQLException
	{
		ObservableList<String> themengebiete = FXCollections.observableArrayList();
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "SELECT themengebiet FROM Frage where Fragekatalog = "+ "'"+katalog+ "'";
		
		//log.info("Last query: "+query);
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) 
		{
			String s = rs.getString("themengebiet");
			if (!themengebiete.contains(s)) {
				themengebiete.add(s);
			}
		}
		return themengebiete;
	}
	
	//Changes 25.11 -Gjergji
	public ObservableList<String> katalogeAuslesen() throws SQLException
	{
		ObservableList<String> kataloge = FXCollections.observableArrayList();
		Statement stmt = DBConn.connection.createStatement();
		
		String query = "SELECT Fragekatalog FROM Frage";
		
		//log.info("Last query: "+query);
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()) 
		{
			String s = rs.getString("Fragekatalog");
			if (!kataloge.contains(s)) {
				kataloge.add(s);
			}
		}
		return kataloge;
	}
	
}
