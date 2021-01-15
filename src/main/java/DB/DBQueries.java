package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

import com.mysql.cj.x.protobuf.MysqlxSql.StmtExecute;

import de.hftstuttgart.EasyExam.Controllers.LoginController;
import de.hftstuttgart.EasyExam.Models.Frage;
import de.hftstuttgart.EasyExam.Models.Note;
import de.hftstuttgart.EasyExam.Models.Pruefer;
import de.hftstuttgart.EasyExam.Models.Pruefung;
import de.hftstuttgart.EasyExam.Models.Student;
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
	
	public DBQueries(Connection conn) {
		connection = conn;
	} 
	
	/**
	 * The method is used to save points for a question into the database 
	 * 
	 * @param frage Frage object 
	 * @param punkte double 
	 * @throws SQLException
	 */
	public void erreichte_punkte_speichern(Frage frage, double punkte) throws SQLException {
		connection.setAutoCommit(true);
		String query = "update Frage set Punkte_erreicht = ? where idFrage = ?";
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		
		preparedStmt.setDouble(1, punkte);
		preparedStmt.setInt(2, frage.getID());
		log.info("Last query: "+ preparedStmt);
		preparedStmt.executeUpdate();
	}
	
	/**
	 * Add notes to a question - DB - Notizien: Deafult = NULL, Reset on protokollieren() / exam end.
	 * 
	 * @param note Note object 
	 * @param frage Frage object 
	 * @return int, which shows if the notes are saved 
	 * @throws SQLException
	 */
	public int notizienSpeichern(Note note, Frage frage) throws SQLException {
		
		//set to false
		connection.setAutoCommit(true);
		
		try {
			  String query = "update Frage set Notizien = ? where idFrage = ?";
		      PreparedStatement preparedStmt = connection.prepareStatement(query);
		      preparedStmt.setString   (1, note.getText());
		      preparedStmt.setInt   (2, frage.getID());
		      
		      log.info(preparedStmt.toString());
		      
		      return preparedStmt.executeUpdate();
			
		} catch (SQLException e) {
			log.warning("Notes not saved in the database!" +e.getMessage() +e.getCause());
			e.printStackTrace();
			return 0;
		}
				
	}

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
	public int frageSpeichern(Frage frage) throws SQLException {
		connection.setAutoCommit(true);

		String query = "INSERT INTO FRAGE(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stmt = connection.prepareStatement(query);

		String fragestellung = frage.getFrageStellung();
		String musterloesung = frage.getMusterloesung();
		String themengebiet = frage.getThemengebiet();
		String fragekatalog = frage.getFragekatalog();
		String modul = frage.getModul(); 				//TODO!
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
	
	/**
	 * The method is used to select a question from the database 
	 * 
	 * @param frage Frage object 
	 * @return
	 * @throws SQLException
	 */
	public ResultSet frage_selektieren(Frage frage) throws SQLException {
		int idFrage = frage.getID();
		connection.setAutoCommit(true);
		String query = "Select * from Frage where idFrage = "+idFrage;
		Statement stmt = connection.createStatement();
		
		return DBQueries.rs = stmt.executeQuery(query);
	}
	
	/**
	 * Takes a list of students form the Controller as a paramter and saves it'c content into the DB
	 * 
	 * @param studenten ObservaleList with Student objects 
	 * @return int, which shows if the student is saved
	 * @throws SQLException
	 */
	public int studentenSpeichern(ObservableList<Student> studenten) throws SQLException {
		connection.setAutoCommit(true);
		
		//0 update unsuccessful- 1 update successful
		int status = 0;
		
		String query = "INSERT INTO Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) "
				+ "VALUES(?,?,?,?,?)";
		PreparedStatement stmt = connection.prepareStatement(query);

		
		for (Student student : studenten) {
			int matrikelnr = student.getMatrikelnr();
			String nachname = student.getNachname();
			String vorname = student.getVorname();
			int semester = student.getSemester();
			String studiengang = student.getStudiengang();
			
			stmt.setInt(1, matrikelnr);
			stmt.setString(2, nachname);
			stmt.setString(3, vorname);
			stmt.setInt(4, semester);
			stmt.setString(5, studiengang);
			
			
			log.info("Student : " + student.toString());
			log.info("Query : 	" + query);
			status = stmt.executeUpdate();
	
		}
		
		//Successful - Unsuccessful ?
		return status;
	}

	/**
	 * Load all questions
	 * 
	 * @param katalog
	 * @return
	 * @throws SQLException
	 */
	public ResultSet alleFrageLaden(String katalog) throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "SELECT * FROM Frage where Fragekatalog = " + "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}
	
	/**
	 * The method is used to load all students from the database 
	 * 
	 * @return ResultSet with students 
	 * @throws SQLException
	 */
	public ResultSet studentenLaden() throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		String query = "Select * FROM Student";
		
		return DBQueries.rs = stmt.executeQuery(query);
		
		
	}

	/**
	 * Load questions based on topic
	 * 
	 * @param themengebiet
	 * @param katalog
	 * @return
	 * @throws SQLException
	 */
	public ResultSet frageLaden_themengebiet(String themengebiet, String katalog) throws SQLException {
		
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "Select * from Frage where themengebiet =" + "'" + themengebiet + "'" + " and Fragekatalog = "
				+ "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);
	}

	/**
	 * Load questions based on different level
	 * 
	 * @param niveau
	 * @param katalog
	 * @return
	 * @throws SQLException
	 */
	public ResultSet frageLaden_niveau(int niveau, String katalog) throws SQLException {
		String niv = Integer.toString(niveau);
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "Select * from Frage where niveau =" + "'" + niv + "'" + " and Fragekatalog = " + "'" + katalog
				+ "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}

	/**
	 * Load questions based on diff. level and topic
	 * 
	 * @param niveau
	 * @param themengebiet
	 * @param katalog
	 * @return
	 * @throws SQLException
	 */
	public ResultSet frageLaden_niveau_themengebiet(int niveau, String themengebiet, String katalog)
			throws SQLException {
		String niv = Integer.toString(niveau);
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "Select * from Frage where niveau = " + "'" + niv + "'" + " and themengebiet = " + "'"
				+ themengebiet + "'" + " and Fragekatalog = " + "'" + katalog + "'";

		log.info("Last query: " + query);
		return DBQueries.rs = stmt.executeQuery(query);
	}

	/**
	 * Load all asked questions
	 * 
	 * @param katalog
	 * @return
	 * @throws SQLException
	 */
	public ResultSet fragenLaden_gestellt(String katalog) throws SQLException {

		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "SELECT * from Frage WHERE gestellt = 1 " + "AND Fragekatalog = " + "'" + katalog + "'";

		log.info("Result Set: " + query);
		return DBQueries.rs = stmt.executeQuery(query);

	}

	/**
	 * Ask a question to a student - change boolean property of the question
	 * 
	 * @param frage
	 * @param gestellt
	 * @return
	 * @throws SQLException
	 */
	public int frageStellen(Frage frage, Boolean gestellt) throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		int intGestellt = gestellt ? 1 : 0;
		int id = frage.getID();
		String query = "UPDATE Frage SET gestellt = " + intGestellt + " where idFrage = " + id;

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}

	
	/**
	 * Delete a single question
	 * 
	 * @param ID
	 * @throws SQLException
	 */
	public void frageLoeschen(int ID) throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		
		String query = "DELETE FROM Frage WHERE idFrage = " + ID;
		
			int i = stmt.executeUpdate(query);
			
			if (i == 1)	
			log.info("Question: " + ID + "succesfully deleted");

	}

	/**
	 * Delete an entire catalog of questions
	 * 
	 * @param katalog
	 * @throws SQLException
	 */
	public void katalogLoeschen(String katalog) throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "DELETE FROM Frage WHERE Fragekatalog = " + "'" + katalog + "'";
		log.info("Last query: " + query);
		int i = stmt.executeUpdate(query);

		if (i == 1)
		log.info(katalog + " succesfully deleted");

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
		Statement stmt = connection.createStatement();

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

	/**
	 * 
	 * 
	 * @return ObservableList with Fragekatalog
	 * @throws SQLException
	 */
	public ObservableList<String> katalogeAuslesen() throws SQLException {
		ObservableList<String> kataloge = FXCollections.observableArrayList();
		Statement stmt = connection.createStatement();

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

	/**
	 * Old method for saving questions, no longer used
	 * 
	 * @param fragestellung
	 * @param musterloesung
	 * @param niveau
	 * @param punkte
	 * @param gestellt
	 * @param themengebiet
	 * @param fragekatalog
	 * @param modul
	 * @return
	 * @throws SQLException
	 */
	public int frageSpeichern_SIDB(String fragestellung, String musterloesung, int niveau, double punkte,
			String gestellt, String themengebiet, String fragekatalog, String modul) throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		String query = "INSERT INTO Frage(Fragestellung, Musterloesung, Niveau, Punkte, gestellt, themengebiet, Fragekatalog, Modul) "
				+ "Values('" + fragestellung + "','" + musterloesung + "', '" + niveau + "', '" + punkte + "', '"
				+ gestellt + "', '" + themengebiet + "', '" + fragekatalog + "', '" + modul + "')";

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}

	/**
	 * The method is used to load the reached points from a question
	 * 
	 * @param frage
	 * @return
	 * @throws SQLException
	 */
	public Float select_erreichte_punkte(Frage frage) throws SQLException {
		String query = "SELECT Punkte_erreicht FROM Frage WHERE idFrage = " + frage.getID();
		Statement stmt = connection.createStatement();
		DBQueries.rs = stmt.executeQuery(query);
		if (DBQueries.rs.next()) {
			return DBQueries.rs.getFloat("Punkte_erreicht");
		} else
			return null;
	}

	/**
	 * set gestellt=false for all methods 
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int setAllFalse() throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();

		String query = "UPDATE Frage SET gestellt = 0";

		log.info("Last query: " + query);
		return stmt.executeUpdate(query);
	}
	
	/**
	 * The method is used to unask questions, delete notes and set points=0
	 * 
	 * @throws SQLException
	 */
	public void reset() throws SQLException {
		connection.setAutoCommit(true);
		Statement stmt = connection.createStatement();
		
		String query_gestellt = "UPDATE Frage SET gestellt = 0";
		String query_notes = "UPDATE Frage SET Notizien = null";
		String query_erreichte_punkte = "UPDATE Frage SET Punkte_erreicht = 0";
		
		
		int a = stmt.executeUpdate(query_gestellt);
		if (a == 1)log.info("Res bool: " +stmt);
		int b = stmt.executeUpdate(query_notes);
		if (b == 1)log.info("Res Notes: " +stmt);
		int c =stmt.executeUpdate(query_erreichte_punkte);
		//System.out.print("c = " +c);
		if (c == 1)log.info("Res ERP: " +stmt);
	
		
	}
	
	/**
	 * The method is used to load LoginData for one user from the Database 
	 * 
	 * @param email String with email address
	 * @return ResultSet with LoginData
	 * @throws SQLException
	 */
	public ResultSet getLoginData(String email) throws SQLException{
		Statement stmt = connection.createStatement();
		String query = "SELECT eMail, Passwort FROM pruefer WHERE eMail=" + "'" + email + "'";
		return stmt.executeQuery(query);
	}
	
	/**
	 * The method is used to load an examiner
	 * 
	 * @param prueferMail String with Email address  
	 * @return Pruefer object 
	 * @throws SQLException
	 */
	public Pruefer getPruefer (String prueferMail) throws SQLException {
		
			Statement stmt = connection.createStatement();
			String query = "SELECT PersNr, Nachname, Vorname from Pruefer WHERE email = " + "'" + prueferMail + "'";
			ResultSet rs = stmt.executeQuery(query);
			Pruefer pruefer = null;
			while(rs.next()) {	
			pruefer = new Pruefer(rs.getInt("PersNr"), rs.getString("Nachname"), rs.getString("Vorname"));
			}
			return pruefer;

	}
	/**
	 * The method is used to save an exam per student
	 * 
	 * @param pruefung
	 * @return 
	 * @throws SQLException
	 */

	



	public int pruefungSpeichern(Pruefung pruefung) throws SQLException {
		connection.setAutoCommit(true);

		String query = "INSERT INTO Pruefung(Bezeichnung, Note, Punkte_gesamt, Matrikelnr, PersNr) "
				+ "VALUES(?,?,?,?,?)";

		PreparedStatement stmt = connection.prepareStatement(query);
		
		String bezeichnung = pruefung.getBezeichnung();
		float note = pruefung.getNote();
		float punkte_gesamt = pruefung.getPunkteGesamt();
		int matrikelnr = pruefung.getStudent_mtkr();
		int persnr = pruefung.getPrueferNr();
				
		stmt.setString(2, bezeichnung);
		stmt.setFloat(3, note);
		stmt.setFloat(4, punkte_gesamt);
		stmt.setInt(5, matrikelnr);
		stmt.setInt(6, persnr);
	

		return stmt.executeUpdate();
	}
	

		
	
	
	
	public ObservableList<Pruefung> allePruefung(String katalog) throws SQLException {
		
		Statement stmt = connection.createStatement();
		String query = "SELECT * FROM pruefung where Bezeichnung =" + "'" + katalog + "'";

		ResultSet rs = stmt.executeQuery(query);	
		Pruefung pruefung = null;
		ObservableList<Pruefung> pruefungsList = FXCollections.observableArrayList();
		
		while (rs.next()) {
			
			pruefung = new Pruefung(rs.getInt("idPruefung"), rs.getString("Bezeichnung"), rs.getFloat("Note"), rs.getFloat("Punkte_gesamt"), rs.getInt("Matrikelnr"), rs.getInt("PersNr"));

			pruefungsList.add(pruefung);			
		} 
		return pruefungsList;
		
		
		
		
	}

	

}
