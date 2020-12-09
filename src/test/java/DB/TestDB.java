package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class TestDB {
    
    private static final TestDB testDB = new TestDB();
    static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }
    /**
     * empty constructor for class TestDB 
     */
    private TestDB(){
    }
    
    /**
     * Method to get the instance of TestDB
     * 
     * @return instance of TestDB
     */
    public static TestDB getInstance(){
        return testDB;
    }
    
    /**
     * Method to build DB connection
     */
    public void initDBConnection() {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method to create tables and fill in test data 
     */
    public void handleDB() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(	"DROP TABLE IF EXISTS Pruefung;" + 
            					"DROP TABLE IF EXISTS Fragenloesung;" + 
            					"DROP TABLE IF EXISTS Frage;" + 
            					"DROP TABLE IF EXISTS Fragekatalog;" + 
            					"DROP TABLE IF EXISTS Themengebiet;" + 
            					"DROP TABLE IF EXISTS Modul;" + 
            					"DROP TABLE IF EXISTS Pruefer;" + 
            					"DROP TABLE IF EXISTS Student;" + 
            					"DROP TABLE IF EXISTS Musterloesung;");
            stmt.executeUpdate(	"CREATE TABLE Student" + 
            					"(Matrikelnr INTEGER PRIMARY KEY," + 
            					"Nachname VARCHAR(45) NOT NULL," + 
            					"Vorname VARCHAR(45) NOT NULL," + 
            					"Semester INTEGER," + 
            					"Studiengang VARCHAR(45));" + 
            					"CREATE TABLE Pruefer" + 
            					"(PersNr INTEGER PRIMARY KEY," + 
            					"Nachname VARCHAR(45) NOT NULL," + 
            					"Vorname VARCHAR(45) NOT NULL);" + 
            					"CREATE TABLE Frage" + 
            					"(idFrage INTEGER PRIMARY KEY AUTOINCREMENT," + 
            					"Fragestellung text NOT NULL," + 
            					"Musterloesung text," + 
            					"Niveau INTEGER NOT NULL," + 
            					"Punkte FLOAT NOT NULL," + 
            					"gestellt TINYINT," + 
            					"Themengebiet VARCHAR(255)," + 
            					"Fragekatalog VARCHAR(255)," + 
            					"Modul VARCHAR(255)," + 
            					"grundLageNiveau VARCHAR(255)," + 
            					"gut VARCHAR(255)," + 
            					"sehrGut VARCHAR(255));" + 
            					"CREATE TABLE Pruefung" + 
            					"(idPruefung INTEGER PRIMARY KEY," + 
            					"Bezeichnung VARCHAR(45) NOT NULL," + 
            					"Note FLOAT," + 
            					"Fragekatalog_fk INTEGER," + 
            					"Matrikelnr INTEGER," + 
            					"PersNr INTEGER," + 
            					"CONSTRAINT fk_PersNr FOREIGN KEY (PersNr) REFERENCES Pruefer (PersNr) ON DELETE CASCADE ON UPDATE CASCADE," + 
            					"CONSTRAINT fk_Matrikelnr FOREIGN KEY (Matrikelnr) REFERENCES Student (Matrikelnr) ON DELETE CASCADE ON UPDATE CASCADE);");
            stmt.execute(	"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123456,'Speiser','Sebastian');" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123455,'Höss','Oliver');" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123457,'Kramer','Ralf');" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123454,'Lueckemeyer','Gero');" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123453,'Dehdari','Payam');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (121212, 'Hartwig', 'Lorenz', 6, 'WI');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (131313, 'Jakobi', 'Jana', 5, 'WI');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (141414, 'Alnaser', 'Bachir', 5, 'WI');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (151515, 'Kallenberger', 'Ruth', 5, 'WI');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (161616, 'Shkurti', 'Gjergji', 6, 'WI');" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (171717, 'Durmus', 'Esma', 6, 'WI');" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Was ist 1+1?', 'Die Lösung ist 2.', 1, 1.5, 0, 'Business Process Model and Notation','QM SS2019', 'QM', 'Level 1', 'Level 2', 'Level 3');" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Für was steht QM?', 'QM steht für Qualitäsmanagement.', 2, 3.0, 0, 'Business Process Model and Notation','GPM WS 2020', 'GPM', 'Level 1', 'Level 2', 'Level 3');" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wie groß ist eine Europalette?', 'Eine Europalette ist 80x120cm groß.', 3, 2.5, 0, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wofür steht OOP?', 'OOP steht für objektorientierte Programmierung.', 1, 1.5, 1, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');" + 
            				"Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (1423, 'Qualitaetsmanagement SS2020', 1.0, 943829, 121212, 123454);" + 
            				"Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (3425, 'Beschaffung und Logistik WS20/21', 1.7, 239857, 161616, 123453);");
            
            System.out.println("Datenbank erstellt");
           /*PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, "Willi Winzig");
            ps.setString(2, "Willi's Wille");
            ps.setDate(3, Date.valueOf("2011-05-16"));
            ps.setInt(4, 432);
            ps.setDouble(5, 32.95);
            ps.addBatch();

            ps.setString(1, "Anton Antonius");
            ps.setString(2, "Anton's Alarm");
            ps.setDate(3, Date.valueOf("2009-10-01"));
            ps.setInt(4, 123);
            ps.setDouble(5, 98.76);
            ps.addBatch();

            connection.setAutoCommit(false);
            ps.executeBatch();
            connection.setAutoCommit(true);*/

           /* ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
            while (rs.next()) {
                System.out.println("Autor = " + rs.getString("author"));
                System.out.println("Titel = " + rs.getString("title"));
                System.out.println("Erscheinungsdatum = "
                        + rs.getDate("publication"));
                System.out.println("Seiten = " + rs.getInt("pages"));
                System.out.println("Preis = " + rs.getDouble("price"));*/
           // rs.close();
        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }
    
    /**
     * Method to close the Database connection 
     */
    public void closeConnection(){
    	try{
    		connection.close();
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

   /* public static void main(String[] args) {
        TestDB dbc = TestDB.getInstance();
        dbc.initDBConnection();
        dbc.handleDB();
    }*/
}