package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBQuerys {

	
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
				+ "VALUES('" + fragestellung + "','" + musterloesung + "', '" + niveau +"', '" + punkte + "', '" + gestellt + "', '" + themengebiet + "', '" + fragekatalog + "', '" + modul +"')";
		return stmt.executeUpdate(query);
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
	
}
