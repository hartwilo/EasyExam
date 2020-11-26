package DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBQueries {
	
	private static final Logger log;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
		log = Logger.getLogger(DBConn.class.getName());
	}

	

	
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
				+ "Values('" + fragestellung + "','" + musterloesung + "', '" + niveau +"', '" + punkte + "', '" + gestellt + "', '" + themengebiet + "', '" + fragekatalog + "', '" + modul +"')";
		log.info(query);
		return stmt.executeUpdate(query);
	}
	
	
	///Changes 25.11 -Gjergji TODO add logic for Katalog Name to query
	public ResultSet alleFrageLaden() throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "SELECT * FROM Frage";
		log.info(query);
		return DBQueries.rs = stmt.executeQuery(query);
		
	}
	
	//Changes 26.11 - Gjergji Select questions based on topic
	public ResultSet frageLaden_themengebiet(String themengebiet) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "Select * from Frage where themengebiet =" + "'" + themengebiet + "'";
		log.info(query);
		return DBQueries.rs = stmt.executeQuery(query);
	}
	
	//Changes 26.11 - Gjergji Select questions based on diff. level
	public ResultSet frageLaden_niveau(int niveau) throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "Select * from Frage where niveau =" + "'" + niv + "'";
		log.info(query);
		return DBQueries.rs = stmt.executeQuery(query);
		
	}
	
	////Changes 26.11 - Gjergji Select questions based on diff. level and topic
	public ResultSet frageLaden_niveau_themengebiet(int niveau, String themengebiet) throws SQLException {
		String niv = Integer.toString(niveau);
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "Select * from Frage where niveau = " + "'" + niv + "'" + " and themengebiet = " + "'"
				+ themengebiet + "'";
		log.info(query);
		return DBQueries.rs = stmt.executeQuery(query);
	}
	
	
	//Changes 25.11 -Gjergji
	public void frageLoeschen(int ID) throws SQLException {
		DBConn.connection.setAutoCommit(true);
		Statement stmt = DBConn.connection.createStatement();
		String query = "DELETE FROM Frage WHERE idFrage = " + ID;
		log.info(query);
		stmt.executeUpdate(query);	
	}
	
	/**
	 * SQL-Query to get subject areas from Database
	 * 
	 * @return
	 * @throws SQLException
	 * 
	 */
	public ObservableList<String> themengebieteAuslesen() throws SQLException
	{
		ObservableList<String> themengebiete = FXCollections.observableArrayList();
		Statement stmt = DBConn.connection.createStatement();
		String query = "SELECT themengebiet FROM Frage";
		log.info(query);
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
		log.info(query);
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
