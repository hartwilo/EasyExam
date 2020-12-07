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
    private static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }
    
    private TestDB(){
    }
    
    public static TestDB getInstance(){
        return testDB;
    }
    
    private void initDBConnection() {
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

    private void handleDB() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(	"DROP TABLE IF EXISTS Pruefung;\r\n" + 
            					"DROP TABLE IF EXISTS Fragenloesung;\r\n" + 
            					"DROP TABLE IF EXISTS Frage;\r\n" + 
            					"DROP TABLE IF EXISTS Fragekatalog;\r\n" + 
            					"DROP TABLE IF EXISTS Themengebiet;\r\n" + 
            					"DROP TABLE IF EXISTS Modul;\r\n" + 
            					"DROP TABLE IF EXISTS Pruefer;\r\n" + 
            					"DROP TABLE IF EXISTS Student;\r\n" + 
            					"DROP TABLE IF EXISTS Musterloesung;");
            stmt.executeUpdate(	"CREATE TABLE Student\r\n" + 
            					"(Matrikelnr INTEGER PRIMARY KEY,\r\n" + 
            					"Nachname VARCHAR(45) NOT NULL,\r\n" + 
            					"Vorname VARCHAR(45) NOT NULL,\r\n" + 
            					"Semester INTEGER,\r\n" + 
            					"Studiengang VARCHAR(45));\r\n" + 
            					"\r\n" + 
            					"CREATE TABLE Pruefer\r\n" + 
            					"(PersNr INTEGER PRIMARY KEY,\r\n" + 
            					"Nachname VARCHAR(45) NOT NULL,\r\n" + 
            					"Vorname VARCHAR(45) NOT NULL);\r\n" + 
            					"\r\n" + 
            					"CREATE TABLE Frage\r\n" + 
            					"(idFrage INTEGER PRIMARY KEY auto_increment,\r\n" + 
            					"Fragestellung text NOT NULL,\r\n" + 
            					"Musterloesung text,\r\n" + 
            					"Niveau INTEGER NOT NULL,\r\n" + 
            					"Punkte FLOAT NOT NULL,\r\n" + 
            					"gestellt TINYINT,\r\n" + 
            					"Themengebiet VARCHAR(255),\r\n" + 
            					"Fragekatalog VARCHAR(255),\r\n" + 
            					"Modul VARCHAR(255),\r\n" + 
            					"grundLageNiveau VARCHAR(255),\r\n" + 
            					"gut VARCHAR(255),\r\n" + 
            					"sehrGut VARCHAR(255));\r\n" + 
            					"\r\n" + 
            					"CREATE TABLE Pruefung\r\n" + 
            					"(idPruefung INTEGER PRIMARY KEY,\r\n" + 
            					"Bezeichnung VARCHAR(45) NOT NULL,\r\n" + 
            					"Note FLOAT,\r\n" + 
            					"Fragekatalog_fk INTEGER,\r\n" + 
            					"Matrikelnr INTEGER,\r\n" + 
            					"PersNr INTEGER,\r\n" + 
            					"CONSTRAINT fk_PersNr FOREIGN KEY (PersNr) REFERENCES Pruefer (PersNr) ON DELETE CASCADE ON UPDATE CASCADE,\r\n" + 
            					"CONSTRAINT fk_Matrikelnr FOREIGN KEY (Matrikelnr) REFERENCES Student (Matrikelnr) ON DELETE CASCADE ON UPDATE CASCADE);\r\n" + 
            					"\r\n" + 
            					"CREATE TABLE Fragenloesung\r\n" + 
            					"(idFragenloesung INTEGER PRIMARY KEY, \r\n" + 
            					"Niveau INTEGER NOT NULL,\r\n" + 
            					"Frage_fk INTEGER,\r\n" + 
            					"CONSTRAINT fk_Frage FOREIGN KEY (Frage_fk) REFERENCES Frage (idFrage) ON DELETE CASCADE ON UPDATE CASCADE);");
            stmt.execute(	"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123456,'Speiser','Sebastian');\r\n" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123455,'Höss','Oliver');\r\n" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123457,'Kramer','Ralf');\r\n" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123454,'Lueckemeyer','Gero');\r\n" + 
            				"Insert Into Pruefer(PersNr, Nachname, Vorname) Values (123453,'Dehdari','Payam');\r\n" + 
            				"\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (121212, 'Hartwig', 'Lorenz', 6, 'WI');\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (131313, 'Jakobi', 'Jana', 5, 'WI');\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (141414, 'Alnaser', 'Bachir', 5, 'WI');\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (151515, 'Kallenberger', 'Ruth', 5, 'WI');\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (161616, 'Shkurti', 'Gjergji', 6, 'WI');\r\n" + 
            				"Insert Into Student(Matrikelnr, Nachname, Vorname, Semester, Studiengang) Values (171717, 'Durmus', 'Esma', 6, 'WI');\r\n" + 
            				"\r\n" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Was ist 1+1?', 'Die Lösung ist 2.', 1, 1.5, 0, 'Business Process Model and Notation','QM SS2019', 'QM', 'Level 1', 'Level 2', 'Level 3');\r\n" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Für was steht QM?', 'QM steht für Qualitäsmanagement.', 2, 3.0, 0, 'Business Process Model and Notation','GPM WS 2020', 'GPM', 'Level 1', 'Level 2', 'Level 3');\r\n" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wie groß ist eine Europalette?', 'Eine Europalette ist 80x120cm groß.', 3, 2.5, 0, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');\r\n" + 
            				"Insert Into Frage (Fragestellung, Musterloesung, Niveau, Punkte, gestellt, Themengebiet, Fragekatalog, Modul, grundLageNiveau, gut, sehrGut) Values ('Wofür steht OOP?', 'OOP steht für objektorientierte Programmierung.', 1, 1.5, 1, 'Beschaffungsmanagement', 'Logistik WS2016', 'Logistik', 'Level 1', 'Level 2', 'Level 3');\r\n" + 
            				"\r\n" + 
            				"Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (1423, 'Qualitaetsmanagement SS2020', 1.0, 943829, 121212, 123454);\r\n" + 
            				"Insert Into Pruefung(idPruefung,Bezeichnung,Note,Fragekatalog_fk,Matrikelnr,PersNr) Values (3425, 'Beschaffung und Logistik WS20/21', 1.7, 239857, 161616, 123453);\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"\r\n" + 
            				"Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739851, 42 , 11184729);\r\n" + 
            				"Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739852, 42, 11184730);\r\n" + 
            				"Insert Into Fragenloesung (idFragenloesung, Niveau, Frage_fk) Values (333739853, 42, 11184731);\r\n" + 
            				"\r\n" + 
            				"SELECT * FROM Pruefer;\r\n" + 
            				"\r\n" + 
            				"");
            
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