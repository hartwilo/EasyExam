package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuerys {

	private Connection conn;
	
	/**
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
		
		conn.setAutoCommit(false);
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO Frage(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul) "
				+ "Values('" + fragestellung + "','" + musterloesung + "', '" + niveau +"', '" + punkte + "', '" + gestellt + "', '" + themengebiet + "', '" + fragekatalog + "', '" + modul +"')";
		System.out.println(query);
		return stmt.executeUpdate(query);
	}
	
	
}